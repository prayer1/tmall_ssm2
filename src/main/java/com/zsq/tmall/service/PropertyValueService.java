package com.zsq.tmall.service;

import com.zsq.tmall.pojo.Product;
import com.zsq.tmall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    void init(Product p);
    void update(PropertyValue pv);

    PropertyValue get(int ptid, int pid);
    List<PropertyValue> list(int pid);
}
