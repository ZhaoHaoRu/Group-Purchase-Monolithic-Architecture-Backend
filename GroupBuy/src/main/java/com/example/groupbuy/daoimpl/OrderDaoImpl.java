package com.example.groupbuy.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.groupbuy.dao.OrderDao;
import com.example.groupbuy.entity.*;
import com.example.groupbuy.repository.*;
import com.example.groupbuy.utils.messageUtils.Message;
import com.example.groupbuy.utils.messageUtils.MessageUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    AddressesRepository addressesRepository;

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
                itemInfo.put("picture",goods.getPicture());
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
                itemInfo.put("picture",goods.getPicture());
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
            newObject.put("picture",goods.getPicture());
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
    public void changeInventory(Integer inventory, Integer goodsId){
        goodsRepository.changeInventory(inventory,goodsId);
    }

    @Override
    public void addToOrder(Timestamp time, Integer addressId, Integer orderId){
        ordersRepository.addToOrder(time,addressId,orderId);
    }

    @Override
    public GroupBuying findByGroupId(int id){
        return groupRepository.findByGroupId(id);
    }

    @Override
    public Goods findByGoodsId(Integer goodsId){
        return goodsRepository.findByGoodsId(goodsId);
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
}
