package com.zsq.tmall.service.impl;

import com.zsq.tmall.mapper.ReviewMapper;
import com.zsq.tmall.pojo.Review;
import com.zsq.tmall.pojo.ReviewExample;
import com.zsq.tmall.pojo.User;
import com.zsq.tmall.service.ReviewService;
import com.zsq.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserService userService;
    @Override
    public void add(Review r) {
        reviewMapper.insert(r);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review r) {
        reviewMapper.updateByPrimaryKeySelective(r);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");

        List<Review> result = reviewMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    private void setUser(List<Review> rs) {
        for (Review r : rs) {
            setUser(r);
        }
    }

    private void setUser(Review r) {
        int uid = r.getUid();
        User user = userService.get(uid);
        r.setUser(user);
    }
    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }
}
