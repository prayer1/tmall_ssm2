package com.zsq.tmall.service;

import com.zsq.tmall.pojo.Order;
import com.zsq.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderService {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order o);
    float add(Order o, List<OrderItem> ois);
    void delete(int id);
    Order get(int id);
    void update(Order o);
    List list();
    List list(int uid, String excludedStatus);
}
