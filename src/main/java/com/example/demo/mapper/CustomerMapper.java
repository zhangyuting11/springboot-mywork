package com.example.demo.mapper;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.WorkEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * Created by v_aytzhang on 2017/8/16.
 */
@Mapper
public interface CustomerMapper {
    @Insert("insert into customer(" +
            "appid," +
            "bizid," +
            "uin," +
            "name) " +
            "values(" +
            "#{appid,jdbcType=VARCHAR}," +
            "#{bizid,jdbcType=VARCHAR}," +
            "#{uin,jdbcType=VARCHAR}," +
            "#{name,jdbcType=VARCHAR})")
    int addCustomer(CustomerEntity customerEntity);

    @Select("select appid from customer where bizid = #{bizid,jdbcType=VARCHAR}")
    @Result(property = "bizid", column = "bizid")
    String selectAppid(@Param("bizid") String bizid);

    @Select("select * from customer where bizid = #{bizid,jdbcType=VARCHAR}")
    @Results({
            @Result(property = "appid", column = "appid"),
            @Result(property = "uin", column = "uin"),
            @Result(property = "bizid", column = "bizid"),
            @Result(property = "name", column = "name"),})
    CustomerEntity selectCustomer(@Param("bizid") String bizid);
}
