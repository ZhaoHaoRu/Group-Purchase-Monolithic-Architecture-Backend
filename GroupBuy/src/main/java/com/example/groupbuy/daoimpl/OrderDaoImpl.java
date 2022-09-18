package com.example.groupbuy.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.dao.OrderDao;
import com.example.groupbuy.dao.GroupDao;
import com.example.groupbuy.entity.*;
import com.example.groupbuy.repository.*;
import com.example.groupbuy.utils.messageUtils.Message;
import com.example.groupbuy.utils.messageUtils.MessageUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Resource
    OrdersRepository ordersRepository;

    @Resource
    UserRepository usersRepository;

    @Resource
    GroupRepository groupRepository;

    @Resource
    GoodsRepository goodsRepository;

    @Resource
    OrderItemsRepository orderItemsRepository;

    @Resource
    GoodsPicRepository goodsPicRepository;

    @Resource
    GroupPicRepository groupPicRepository;

    @Resource
    GroupDao groupDao;

    @Override
    public List<JSONObject> getOrderByUserId(int userId){
//        String strId = UserId.getString("userId");
//        Integer userId = Integer.parseInt(strId);
        List<Orders> orderList = ordersRepository.findOrderByUserId(userId);
        List<JSONObject> dataList = new ArrayList<>();
        for (int i=0; i<orderList.size(); ++i){
            Orders order = orderList.get(i);
            JSONObject newObject = new JSONObject();
            Address address = order.getAddress();
            GroupBuying group = order.getGroup();
            newObject.put("time",order.getTime());
            newObject.put("receiver",address.getReceiver());
            newObject.put("phone",address.getPhone());
            newObject.put("region",address.getRegion());
            newObject.put("location",address.getLocation());
            newObject.put("orderId",order.getOrderId());
            newObject.put("state",order.getState());
            newObject.put("groupTitle",group.getGroupTitle());
            newObject.put("groupId",group.getGroupId());
            newObject.put("headerName",group.getUser().getUserName());
            newObject.put("headerId",group.getUser().getUserId());
            newObject.put("delivery",group.getDelivery());
            //该订单的所有订单项
            List<OrderItems> itemList = orderItemsRepository.findItemsByOrderId(order.getOrderId());
            List<JSONObject> itemInfoList = new ArrayList<>();
            BigDecimal totalPrice = BigDecimal.valueOf(0);
            for (int j=0; j<itemList.size(); ++j){
                OrderItems item = itemList.get(j);
                JSONObject itemInfo = new JSONObject();
                Goods goods = item.getGood();
                itemInfo.put("goodsName",goods.getGoodsName());
                itemInfo.put("goodsInfo",goods.getGoodsInfo());
                itemInfo.put("price",goods.getPrice());
                itemInfo.put("number",item.getGoodsNumber());
                /**
                 * 此处获取团购商品对应的图片
                 */
                if(goods.getPicture() != null) {
                    itemInfo.put("picture", goods.getPicture());
                } else {
                    GoodsPic goodsPic = goodsPicRepository.findByGoodsId(goods.getGoodsId());
                    if(goodsPic != null) {
                        itemInfo.put("picture", goodsPic.getPicture());
                    }
                }
                itemInfoList.add(itemInfo);
                BigDecimal unitPrice = goods.getPrice();
                unitPrice = unitPrice.multiply(BigDecimal.valueOf(item.getGoodsNumber()));
                totalPrice = totalPrice.add(unitPrice);
            }
            newObject.put("orderItems",itemInfoList);
            newObject.put("orderPrice",totalPrice);
            dataList.add(newObject);
        }
        return dataList;
    }
    @Override
    public List<JSONObject> getOrderInfo(int userId){
//        String strId = UserId.getString("userId");
//        Integer userId = Integer.parseInt(strId);
        System.out.println("userId:");
        System.out.println(userId);
        User user = usersRepository.findByUserId(userId);
        if(user == null)
            return null;
        Set<GroupBuying> groups = user.getCreateGroups();
        List<Orders> orderList = new ArrayList<>();
        for(GroupBuying group : groups) {
            List<Orders> orders = ordersRepository.findValidByGroupId(group.getGroupId());
            orderList.addAll(orders);
        }
        // TODO: 这个方法貌似不大可行
//        List<Orders> orderList = ordersRepository.findOrderByTuanzhangId(userId);
        System.out.println("orderList number:");
        System.out.println(orderList.size());
        List<JSONObject> dataList1 = new ArrayList<>();
        List<JSONObject> dataList2 = new ArrayList<>();
        int m = 0,n=0;
        BigDecimal totalRefund = BigDecimal.valueOf(0);
        BigDecimal totalSales = BigDecimal.valueOf(0);
        for (int i=0; i<orderList.size(); ++i){
            Orders order = orderList.get(i);
            JSONObject newObject1 = new JSONObject();
            JSONObject newObject2 = new JSONObject();
            Address address = order.getAddress();
            GroupBuying group = order.getGroup();
            if(order.getState() == 1){
                newObject1.put("id", m);
                newObject1.put("groupTitle",group.getGroupTitle());
                m++;
                //newObject.put("groupId",group.getGroupId());
                //newObject.put("headerName",group.getUser().getUserName());
                //newObject.put("headerId",group.getUser().getUserId());
                //newObject.put("delivery",group.getDelivery());
                //该订单的所有订单项
                List<OrderItems> itemList = orderItemsRepository.findItemsByOrderId(order.getOrderId());
                List<JSONObject> itemInfoList = new ArrayList<>();
                BigDecimal totalPrice = BigDecimal.valueOf(0);
                for (int j=0; j<itemList.size(); ++j){
                    OrderItems item = itemList.get(j);
                    JSONObject itemInfo = new JSONObject();
                    Goods goods = item.getGood();
                    //itemInfo.put("goodsName",goods.getGoodsName());
                    //itemInfo.put("goodsInfo",goods.getGoodsInfo());
                    //itemInfo.put("price",goods.getPrice());
                    //itemInfo.put("number",item.getGoodsNumber());
                    /**
                     * 此处获取团购商品对应的图片
                     */
                    if(goods.getPicture() != null) {
                        itemInfo.put("picture", goods.getPicture());
                    } else {
                        GoodsPic goodsPic = goodsPicRepository.findByGoodsId(goods.getGoodsId());
                        if(goodsPic != null) {
                            itemInfo.put("picture", goodsPic.getPicture());
                        }
                    }
                    itemInfoList.add(itemInfo);
                    BigDecimal unitPrice = goods.getPrice();
                    unitPrice = unitPrice.multiply(BigDecimal.valueOf(item.getGoodsNumber()));
                    totalPrice = totalPrice.add(unitPrice);
                    System.out.println("totalPrice:");
                    System.out.println(totalPrice);
                }
                //newObject.put("orderItems",itemInfoList);
                newObject1.put("orderPrice",totalPrice);
                totalSales = totalSales.add(totalPrice);
                dataList1.add(newObject1);
            }
            else if(order.getState() == 2){
                newObject2.put("id",n);
                newObject2.put("groupTitle",group.getGroupTitle());
                n++;
                //newObject.put("groupId",group.getGroupId());
                //newObject.put("headerName",group.getUser().getUserName());
                //newObject.put("headerId",group.getUser().getUserId());
                //newObject.put("delivery",group.getDelivery());
                //该订单的所有订单项
                List<OrderItems> itemList = orderItemsRepository.findItemsByOrderId(order.getOrderId());
                List<JSONObject> itemInfoList = new ArrayList<>();
                BigDecimal totalPrice = BigDecimal.valueOf(0);
                for (int j=0; j<itemList.size(); ++j){
                    OrderItems item = itemList.get(j);
                    JSONObject itemInfo = new JSONObject();
                    Goods goods = item.getGood();
                    //itemInfo.put("goodsName",goods.getGoodsName());
                    //itemInfo.put("goodsInfo",goods.getGoodsInfo());
                    //itemInfo.put("price",goods.getPrice());
                    //itemInfo.put("number",item.getGoodsNumber());
                    /**
                     * 此处获取团购商品对应的图片
                     */
                    if(goods.getPicture() != null) {
                        itemInfo.put("picture", goods.getPicture());
                    } else {
                        GoodsPic goodsPic = goodsPicRepository.findByGoodsId(goods.getGoodsId());
                        if(goodsPic != null) {
                            itemInfo.put("picture", goodsPic.getPicture());
                        }
                    }
                    itemInfoList.add(itemInfo);
                    BigDecimal unitPrice = goods.getPrice();
                    unitPrice = unitPrice.multiply(BigDecimal.valueOf(item.getGoodsNumber()));
                    totalPrice = totalPrice.add(unitPrice);
                }
                //newObject.put("orderItems",itemInfoList);
                totalRefund = totalRefund.add(totalPrice);
                newObject2.put("orderPrice",totalPrice);
                dataList2.add(newObject2);
            }

        }
        List<JSONObject> finaldata = new ArrayList<>();
        JSONObject newObject1 = new JSONObject();
        newObject1.put("id",0);
        newObject1.put("title","销售订单");
        newObject1.put("data",dataList1);

        JSONObject newObject2 = new JSONObject();
        newObject2.put("id",1);
        newObject2.put("title","退款订单");
        newObject2.put("data",dataList2);

        /*统计分析所需要的数据*/
        JSONObject newObject3 = new JSONObject();
        newObject3.put("id",2);
        newObject3.put("title","统计分析");
        List<JSONObject> dataList3 = new ArrayList<>();
        // TODO: 这里用int感觉有点问题，price应该是bigDecimal
        int y = 0, x = 0;
        JSONObject newObject4 = new JSONObject();
        newObject4.put("id",0);
        newObject4.put("orderNum","订单数");
        newObject4.put("value",(m+n));
        dataList3.add(newObject4);


        JSONObject newObject5 = new JSONObject();
        newObject5.put("id",1);
        newObject5.put("title","下单人数");
        newObject5.put("value",m);
        dataList3.add(newObject5);


        for (JSONObject json : dataList1){
            x += json.getInteger("orderPrice");
        }
        JSONObject newObject6 = new JSONObject();
        newObject6.put("id",2);
        newObject6.put("title","总销售金额");
        newObject6.put("value", totalSales);
        dataList3.add(newObject6);


        for (JSONObject json : dataList2){
            y+=json.getInteger("orderPrice");
        }
        JSONObject newObject7 = new JSONObject();
        newObject7.put("id",3);
        newObject7.put("title","总退款金额");
        newObject7.put("value",totalRefund);
        dataList3.add(newObject7);


        newObject3.put("data",dataList3);
        finaldata.add(newObject1);
        finaldata.add(newObject2);
        finaldata.add(newObject3);
        return finaldata;
    }

    @Override
    public List<JSONObject> getOrderByGroupId(int groupId){
//        String StrId = GroupId.getString("groupId");
//        Integer groupId = Integer.parseInt(StrId);
        List<Orders> orderList = ordersRepository.findOrderByGroupId(groupId);
        List<JSONObject> dataList = new ArrayList<>();
        for (int i=0; i<orderList.size(); ++i){
            Orders order = orderList.get(i);
            JSONObject newObject = new JSONObject();
            Address address = order.getAddress();
            newObject.put("time",order.getTime());
            newObject.put("receiver",address.getReceiver());
            newObject.put("phone",address.getPhone());
            newObject.put("region",address.getRegion());
            newObject.put("location",address.getLocation());
            newObject.put("orderId",order.getOrderId());
            newObject.put("customerId",order.getUser().getUserId());
            newObject.put("customerName",order.getUser().getUserName());
            //该订单的所有订单项
            List<OrderItems> itemList = orderItemsRepository.findItemsByOrderId(order.getOrderId());
            List<JSONObject> itemInfoList = new ArrayList<>();
            BigDecimal totalPrice = BigDecimal.valueOf(0);
            for (int j=0; j<itemList.size(); ++j){
                OrderItems item = itemList.get(j);
                JSONObject itemInfo = new JSONObject();
                Goods goods = item.getGood();
                itemInfo.put("goodsName",goods.getGoodsName());
                itemInfo.put("goodsInfo",goods.getGoodsInfo());
                itemInfo.put("price",goods.getPrice());
                itemInfo.put("number",item.getGoodsNumber());
//                itemInfo.put("picture",goods.getPicture());
                /**
                 * 此处获取团购商品对应的图片
                 */
                if(goods.getPicture() != null) {
                    itemInfo.put("picture", goods.getPicture());
                } else {
                    GoodsPic goodsPic = goodsPicRepository.findByGoodsId(goods.getGoodsId());
                    if(goodsPic != null) {
                        itemInfo.put("picture", goodsPic.getPicture());
                    }
                }
                itemInfoList.add(itemInfo);
                BigDecimal unitPrice = goods.getPrice();
                unitPrice = unitPrice.multiply(BigDecimal.valueOf(item.getGoodsNumber()));
                totalPrice = totalPrice.add(unitPrice);
            }
            newObject.put("orderItems",itemInfoList);
            newObject.put("orderPrice",totalPrice);
            dataList.add(newObject);
        }

        return dataList;
    }

    @Override
    public Message<JSONObject> getGroupCart(int groupId, int userId){
//        Integer groupId = info.getInteger("groupId");
//        Integer userId = info.getInteger("userId");
        Integer orderId = ordersRepository.getOrderId(userId,groupId);
        List<OrderItems> itemList = orderItemsRepository.findItemsByOrderId(orderId);
        List<JSONObject> dataList = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (int i=0; i<itemList.size(); ++i){
            OrderItems item = itemList.get(i);
            JSONObject newObject = new JSONObject();
            Goods goods = item.getGood();
            newObject.put("goodsName",goods.getGoodsName());
            newObject.put("goodsInfo",goods.getGoodsInfo());
            newObject.put("price",goods.getPrice());
            newObject.put("number",item.getGoodsNumber());
//            newObject.put("picture",goods.getPicture());
            /**
             * 此处获取团购商品对应的图片
             */
            if(goods.getPicture() != null) {
                newObject.put("picture", goods.getPicture());
            } else {
                GoodsPic goodsPic = goodsPicRepository.findByGoodsId(goods.getGoodsId());
                if(goodsPic != null) {
                    newObject.put("picture", goodsPic.getPicture());
                }
            }
            newObject.put("goodsId", goods.getGoodsId());
            dataList.add(newObject);
            int num = item.getGoodsNumber();
            for(int j = 0; j < num; ++j)
                totalPrice = totalPrice.add(goods.getPrice());
        }
        JSONObject result = new JSONObject();
        result.put("totalPrice", totalPrice);
        result.put("cartItems", dataList);
        return MessageUtil.createMessage(MessageUtil.LOGIN_SUCCESS_CODE,MessageUtil.SUCCESS,result);
    }

    @Override
    public List<Orders> findOrderByGroupId(Integer groupId){
        return ordersRepository.findOrderByGroupId(groupId);
    }

    @Override
    public void refundOne(Integer orderId, Integer userId){
        ordersRepository.refundOne(orderId,userId);
    }

    @Override
    public void deleteByGroupId(Integer groupId){
        ordersRepository.deleteByGroupId(groupId);
    }

    @Override
    public void deleteByOrderId(Integer orderId){
        ordersRepository.deleteByOrderId(orderId);
    }

    @Override
    public Orders findByOrderId(Integer orderId){
        return ordersRepository.findByOrderId(orderId);
    }

    @Override
    public Integer getOrderId(Integer userId,Integer groupId){
        return ordersRepository.getOrderId(userId,groupId);
    }

    @Override
    public List<OrderItems> findItemsByOrderId(Integer orderId){
        return orderItemsRepository.findItemsByOrderId(orderId);
    }

    @Override
    public User findByUserId(int id){
        return usersRepository.findByUserId(id);
    }

    @Override
    public void updateWallet(BigDecimal newWallet,Integer userId){
        usersRepository.updateWallet(newWallet,userId);
    }

    @Override
    public void addToWallet(BigDecimal newWallet, Integer userId) {
        usersRepository.addToWallet(newWallet, userId);
    }

    @Override
    public void changeInventory(Integer inventory, Integer goodsId){
        goodsRepository.changeInventory(inventory,goodsId);
    }

    @Override
    public void addToOrder(Timestamp time, Integer addressId, Integer orderId){
        ordersRepository.addToOrder(time,addressId,orderId);
    }

    // TODO: 和groupDao中的函数有重复
    @Override
    public GroupBuying findByGroupId(int id){
        Optional<GroupBuying> groupBuying = groupDao.getGroupById(id);
        if(groupBuying == null) {
            return null;
        } else {
            return groupBuying.get();
        }
    }

    // TODO: 和groupDao中的函数有重复
    @Override
    public Goods findByGoodsId(Integer goodsId){
        return groupDao.getGoodsById(goodsId);
    }

    @Override
    public void saveCart(Orders newCart){
        ordersRepository.save(newCart);
    }

    @Override
    public void saveItem(OrderItems newItem){
        orderItemsRepository.save(newItem);
    }

    @Override
    public void deleteByItemId(Integer itemId){
        orderItemsRepository.deleteById(itemId);
    }

    @Override
    public void changeGoodsNum(Integer goods_number, Integer goods_id, Integer order_id){
        orderItemsRepository.changeGoodsNum(goods_number,goods_id,order_id);
    }

    @Override
    public List<Orders> getGroupAllCarts(Integer groupId){
        return ordersRepository.getGroupAllCarts(groupId);
    }

    @Override
    public void refundOneBack(Integer orderId, Integer userId){
        ordersRepository.refundOneBack(orderId, userId);
    }

    @Override
    public Set<Orders> isOrdered(GroupBuying groupBuying, User user) {
        return ordersRepository.findOrdersByGroupAndUser(groupBuying, user);
    }
}
