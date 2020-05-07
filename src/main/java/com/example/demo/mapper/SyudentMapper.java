package com.example.demo.mapper;

import com.example.demo.dao.Syudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SyudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Syudent record);

    int insertSelective(Syudent record);

    Syudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Syudent record);

    int updateByPrimaryKey(Syudent record);
}