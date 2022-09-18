package com.example.groupbuy.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.entity.Goods;
import com.example.groupbuy.entity.User;
import com.example.groupbuy.mq.MQSender;
import com.example.groupbuy.service.RedisService;
import com.example.groupbuy.service.SecKillService;
import com.example.groupbuy.service.UserService;
import com.example.groupbuy.utils.messageUtils.Message;
import com.example.groupbuy.utils.messageUtils.MessageUtil;
import com.example.groupbuy.utils.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequestMapping("/seckill")
@RestController
@Component
@Api(tags = "秒杀")
@DependsOn("springContextHolder")
public class SecKillController implements InitializingBean  {

    @Resource
    private SecKillService secKillService;

    @Resource
    private RedisService redisService;

    @Resource
    private MQSender mqSender;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserService userService;



    // 内存标记，减少redis访问
    // 对于在运行期间加入的商品，没有内存标记
    private HashMap<String, Boolean> localOverMap =  new HashMap<String, Boolean>();


    /**
     * 系统初始化的时候把所有的秒杀商品及其库存加入到缓存中
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 查询库存数量
        Set<Goods> goodsSet = secKillService.getAllSecKillGoods();
        if(goodsSet.isEmpty())
            return;
        // 将库存和价格加载到redis中
        for(Goods goods : goodsSet) {
            // 这里加入redis默认为统一的String类型
            String goodsStr = (goods.getGoodsId()).toString();
            redisUtil.getRedisTemplate().opsForValue().set(goodsStr, String.valueOf(goods.getInventory()));
            redisUtil.getRedisTemplate().opsForValue().set("price_" + goodsStr, goods.getPrice().toString());
            // 添加内存标记
            localOverMap.put(goods.getGoodsId().toString(), false);
        }
    }

    /**
     * 在新建团购和修改团购（修改了团购价格）时将商品加入redis
     * @param goods
     */
//    public void addNewProduct(Goods goods) {
//        String goodsStr = goods.getGoodsId().toString();
//        redisUtil.getRedisTemplate().opsForValue().set(goodsStr, String.valueOf(goods.getInventory()));
//        redisUtil.getRedisTemplate().opsForValue().set("price_" + goodsStr, goods.getPrice().toString());
//        // 添加内存标记
//        localOverMap.put(goods.getGoodsId().toString(), false);
//    }

    /**
     * 对于更新了库存了商品进行修改
     * @param goods
     */
//    public void updateProduct(Goods goods) {
//        String goodsStr = goods.getGoodsId().toString();
//        redisUtil.del(goodsStr);
//        redisUtil.getRedisTemplate().opsForValue().set(goodsStr, String.valueOf(goods.getInventory()));
//        // 对于内存中的库存内容进行修改
//        if(goods.getInventory() <= 0) {
//            localOverMap.put(goods.getGoodsId().toString(), true);
//        } else {
//            localOverMap.put(goods.getGoodsId().toString(), false);
//        }
//    }

    /**
     * 秒杀请求,采用redis+rabbitmq的方式
     * @param secKillData
     * @return
     */
    @PostMapping("/purchase")
    @ApiOperation("处理秒杀请求")
    public @ResponseBody Message<String> secKill(@RequestBody JSONObject secKillData) {
        // 处理参数
        Integer userId, addressId, groupId;
        JSONArray goodsData;
        BigDecimal totalPay = BigDecimal.valueOf(0);
        System.out.println(secKillData);
        try {
            userId = secKillData.getInteger("userId");
            groupId = secKillData.getInteger("groupId");
            goodsData = secKillData.getJSONArray("goods");
        } catch (Exception e) {
            return MessageUtil.createMessage(MessageUtil.FAIL_CODE,MessageUtil.FAIL,"参数错误");
        }

        // 校验是否重复下单
        String userStr = userId.toString();
        String groupStr = groupId.toString();
        String map = redisService.get("order_"+userStr + "_" + groupStr, String.class);
        if(map != null) {
            return  MessageUtil.createMessage(MessageUtil.FAIL_CODE,MessageUtil.FAIL,"重复下单");
        }

        // 判断当前商品库存是否足够
        int length = goodsData.size();
        for(int i = 0; i < length; ++i) {
            Map<String, Integer> obj =  (Map<String, Integer>) goodsData.get(i);
            long goodsNumber = obj.get("goodsNumber");
            String goodsId = obj.get("goodsId").toString();
            // 对于没有加入localOverMap中的商品，应该是有货的，现在将其加入
            if(!localOverMap.containsKey(goodsId)) {
                localOverMap.put(goodsId, false);
            }
            if(localOverMap.get(goodsId)) {
                return MessageUtil.createMessage(MessageUtil.FAIL_CODE,MessageUtil.FAIL,"商品库存不足");
            }
            else {
                long stock = redisUtil.decr(goodsId, goodsNumber);
                // 商品已经卖完
                if(stock == 0) {
                    localOverMap.put(goodsId, true);
                } else if(stock < 0){
                    redisUtil.incr(goodsId, goodsNumber);
                    return MessageUtil.createMessage(MessageUtil.FAIL_CODE,MessageUtil.FAIL,"商品库存不足");
                }
            }
            BigDecimal price = new BigDecimal(redisService.get("price_" + goodsId, String.class));
            totalPay = totalPay.add(price.multiply(BigDecimal.valueOf(goodsNumber)));
        }
        // 将团购总消费加入secKillData中
        secKillData.put("price", totalPay);
        // 将下单请求加入到消息队列，客户端轮询或者延迟几秒后查看结果
//        Map<String, Object> msg = new HashMap<>();
        mqSender.send(secKillData);
        return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, "正在排队处理");
    }

    /**
     * 查询秒杀结果
     * @param userId
     * @param groupId
     * @return
     */
    @GetMapping("/secKillResult")
    @ApiOperation("查询秒杀结果")
    public Message<String> secKillResult(@RequestParam("userId") Integer userId, @RequestParam("groupId") Integer groupId) {
        boolean result = secKillService.SecKillResult(userId, groupId);
        if(result == true) {
            return MessageUtil.createMessage(MessageUtil.SUCCESS_CODE, MessageUtil.SUCCESS, "秒杀成功!");
        } else {
            return MessageUtil.createMessage(MessageUtil.FAIL_CODE, MessageUtil.FAIL, "秒杀失败！");
        }
    }
}
