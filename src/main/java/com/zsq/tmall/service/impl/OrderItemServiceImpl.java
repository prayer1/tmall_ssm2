package com.zsq.tmall.service.impl;

import com.zsq.tmall.mapper.OrderItemMapper;
import com.zsq.tmall.pojo.Order;
import com.zsq.tmall.pojo.OrderItem;
import com.zsq.tmall.pojo.OrderItemExample;
import com.zsq.tmall.pojo.Product;
import com.zsq.tmall.service.OrderItemService;
import com.zsq.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductService productService;
    @Override
    public void add(OrderItem oi) {
        orderItemMapper.insert(oi);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem oi) {
        orderItemMapper.updateByPrimaryKeySelective(oi);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem oi = orderItemMapper.selectByPrimaryKey(id);
        setProduct(oi);
        return oi;
    }

    public void setProduct(OrderItem oi) {
        Product p = productService.get(oi.getPid());
        oi.setProduct(p);
    }

    public void setProduct(List<OrderItem> ois) {
        for (OrderItem oi : ois) {
            setProduct(oi);
        }
    }

    @Override
    public List list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);
    }

    @Override
    public void fill(List<Order> os) {
        for (Order o : os) {
            fill(o);
        }
    }

    @Override
    public void fill(Order o) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        setProduct(ois);

        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            total += oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
        }
        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(ois);
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        int result = 0;
        for (OrderItem oi : ois) {
            result += oi.getNumber();
        }
        return result;
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> result = orderItemMapper.selectByExample(example);
        setProduct(result);
        return result;
    }
}
