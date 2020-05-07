package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.example.demo.mapper.SyudentMapper;
import com.example.demo.dao.Syudent;
import com.example.demo.servie.impl.SyudentService;
@Service
public class SyudentServiceImpl implements SyudentService{

    @Autowired
    private SyudentMapper syudentMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return syudentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Syudent record) {
        return syudentMapper.insert(record);
    }

    @Override
    public int insertSelective(Syudent record) {
        return syudentMapper.insertSelective(record);
    }

    @Override
    public Syudent selectByPrimaryKey(Integer id) {
        return syudentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Syudent record) {
        return syudentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Syudent record) {
        return syudentMapper.updateByPrimaryKey(record);
    }

}
