package com.zsq.tmall.service.impl;

import com.zsq.tmall.mapper.PropertyValueMapper;
import com.zsq.tmall.pojo.Product;
import com.zsq.tmall.pojo.Property;
import com.zsq.tmall.pojo.PropertyValue;
import com.zsq.tmall.pojo.PropertyValueExample;
import com.zsq.tmall.service.PropertyService;
import com.zsq.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    private PropertyValueMapper propertyValueMapper;
    @Autowired
    private PropertyService propertyService;
    @Override
    public void init(Product p) {
        List<Property> pts = propertyService.list(p.getCid());
        for (Property pt : pts) {
            PropertyValue pv = get(pt.getId(), p.getId());
            if (null == pv) {
                pv = new PropertyValue();
                pv.setPtid(pt.getId());
                pv.setPid(p.getId());
                propertyValueMapper.insert(pv);
            }
        }
    }

    @Override
    public void update(PropertyValue pv) {
        propertyValueMapper.updateByPrimaryKeySelective(pv);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);
        if (pvs.isEmpty()) {
            return null;
        }
        return pvs.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);
        for (PropertyValue pv : pvs) {
            Property property = propertyService.get(pv.getPtid());
            pv.setProperty(property);
        }
        return pvs;
    }
}
