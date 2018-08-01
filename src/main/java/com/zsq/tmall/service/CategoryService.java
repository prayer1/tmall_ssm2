package com.zsq.tmall.service;

import com.zsq.tmall.pojo.Category;
import com.zsq.tmall.util.Page;

import java.util.List;

public interface CategoryService {
    List<Category> list();
    void add(Category category);
    void delete(int id);
    Category get(int id);
    void update(Category category);
}
