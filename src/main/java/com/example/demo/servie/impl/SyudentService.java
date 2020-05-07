package com.example.demo.servie.impl;

import com.example.demo.dao.Syudent;
public interface SyudentService{


    int deleteByPrimaryKey(Integer id);

    int insert(Syudent record);

    int insertSelective(Syudent record);

    Syudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Syudent record);

    int updateByPrimaryKey(Syudent record);

}
