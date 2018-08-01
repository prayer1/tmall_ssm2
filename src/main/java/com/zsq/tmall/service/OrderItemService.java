package com.zsq.tmall.service;

import com.zsq.tmall.pojo.Order;
import com.zsq.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    void add(OrderItem oi);
    void delete(int id);
    void update(OrderItem oi);
    OrderItem get(int id);
    List list();
    void fill(List<Order> os);
    void fill(Order o);
    int getSaleCount(int pid);
    List<OrderItem> listByUser(int uid);
}
