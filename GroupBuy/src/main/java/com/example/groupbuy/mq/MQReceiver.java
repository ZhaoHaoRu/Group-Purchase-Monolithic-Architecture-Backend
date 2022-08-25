package com.example.groupbuy.mq;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.config.MQConfig;
import com.example.groupbuy.dao.UserDao;
import com.example.groupbuy.entity.User;
import com.example.groupbuy.service.SecKillService;
import io.swagger.models.auth.In;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MQReceiver {

    @Resource
    private SecKillService secKillService;

    @Resource
    private UserDao userDao;

    /**
     * 对于消息队列中的请求进行再次库存判断和秒杀服务
     * @param msg
     */
    @RabbitListener(queues= MQConfig.QUEUE)
    public void receive(JSONObject msg) {
        /**
         * 检查用户余额
         * TODO 放入消息队列后才检查余额是因为这里涉及到数据库中用户信息的查询,猜测比较耗时
         * 如果在放入消息队列前就进行检查更好的话，可以考虑放到controller中
         */
        Integer userId = msg.getInteger("userId");
        User user = userDao.findById(userId);
        BigDecimal price = msg.getBigDecimal("price");
        BigDecimal wallet = user.getWallet();
        // 如果用户余额不足
        if(wallet.compareTo(price) == -1) {
            return;
        }
        Integer addressId = msg.getInteger("addressId");
        Integer groupId = msg.getInteger("groupId");
        JSONArray goodsData = msg.getJSONArray("goods");
        // 执行秒杀请求
        secKillService.SecKillExecution(userId, addressId, groupId, price, goodsData);
    }
}
