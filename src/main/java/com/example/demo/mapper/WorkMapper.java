package com.example.demo.mapper;

import com.example.demo.entity.WorkEntity;
import com.example.demo.response.WorkResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.StringUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by v_aytzhang on 2017/8/15.
 */
@Mapper
public interface WorkMapper {

    @Results({
            @Result(property = "wid", column = "wid"),
            @Result(property = "appid", column = "appid"),
            @Result(property = "content", column = "content"),
            @Result(property = "description", column = "description"),
            @Result(property = "workStatus", column = "work_status"),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updateTime", column = "update_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "startDate", column = "start_date", jdbcType = JdbcType.DATE),
            @Result(property = "endDate", column = "end_date", jdbcType = JdbcType.DATE),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "updateBy", column = "update_by")
    })
    @Select("select * from work order by bizid ")
    List<WorkEntity> getAllWorks();

    @Results(value = {
            @Result(property = "wid", column = "wid"),
            @Result(property = "appid", column = "appid"),
            @Result(property = "content", column = "content"),
            @Result(property = "description", column = "description"),
            @Result(property = "workStatus", column = "work_status"),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updateTime", column = "update_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "startDate", column = "start_date", jdbcType = JdbcType.DATE),
            @Result(property = "endDate", column = "end_date", jdbcType = JdbcType.DATE),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "updateBy", column = "update_by")
    })
    @Select("select * from work where work_status  = #{workStatus,jdbcType=INTEGER} ")
    List<WorkEntity> getWorks(int workStatus);

    @Results(value = {
            @Result(property = "wid", column = "wid"),
            @Result(property = "appid", column = "appid"),
            @Result(property = "content", column = "content"),
            @Result(property = "description", column = "description"),
            @Result(property = "workStatus", column = "work_status"),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updateTime", column = "update_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "startDate", column = "start_date", jdbcType = JdbcType.DATE),
            @Result(property = "endDate", column = "end_date", jdbcType = JdbcType.DATE),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "updateBy", column = "update_by")
    })
    @Select("select * from work " +
            "where wid  = #{wid,jdbcType=INTEGER} ")
    WorkEntity getWorksByPrimarykey(int wid);

    @Insert("insert into work(" +
            "bizid," +
            "content," +
            "description," +
            "start_date," +
            "end_date," +
            "create_by," +
            "create_time," +
            "work_status) " +
            "values(" +
            "#{bizid,jdbcType=VARCHAR}," +
            "#{content,jdbcType=VARCHAR}," +
            "#{description,jdbcType=VARCHAR}," +
            "#{startDate,jdbcType=DATE}," +
            "#{endDate,jdbcType=DATE}," +
            "#{createBy,jdbcType=VARCHAR}," +
            "#{createTime,jdbcType=TIMESTAMP}," +
            "#{workStatus,jdbcType=INTEGER})")
    @Options(keyProperty = "wid", keyColumn = "wid", useGeneratedKeys = true)
    int addWork(WorkEntity workEntity);

    @Update("update work " +
            "set bizid = #{bizid,jdbcType=VARCHAR}," +
            "content = #{content,jdbcType=VARCHAR}," +
            "description = #{description,jdbcType=VARCHAR}," +
            "work_status = #{workStatus,jdbcType=INTEGER} " +
            "where wid = #{wid,jdbcType=INTEGER}")
    int updateObject(WorkEntity workEntity);

    @Update("update work " +
            "set work_status = #{workStatus,jdbcType=INTEGER} ," +
            "update_by = #{updateBy,jdbcType=VARCHAR} ," +
            "update_time = #{updateTime,jdbcType=DATE} " +
            "where wid = #{wid,jdbcType=INTEGER}")
    int updateWork(@Param("workStatus") int workStatus,
                   @Param("wid") int wid,
                   @Param("updateTime") Date updateTime,
                   @Param("updateBy") String updateBy);

    @Results(value = {
            @Result(property = "wid", column = "wid"),
            @Result(property = "appid", column = "appid"),
            @Result(property = "content", column = "content"),
            @Result(property = "description", column = "description"),
            @Result(property = "workStatus", column = "work_status"),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updateTime", column = "update_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "startDate", column = "start_date", jdbcType = JdbcType.DATE),
            @Result(property = "endDate", column = "end_date", jdbcType = JdbcType.DATE),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "updateBy", column = "update_by")
    })
    @Select("select * from work " +
            "where work_status  = #{workStatus,jdbcType=INTEGER} " +
            "and create_time > #{startDate,jdbcType=TIMESTAMP}" +
            "and create_time < #{endDate,jdbcType=TIMESTAMP}")
    List<WorkEntity> getWorksByEntity(WorkEntity workEntity);

    @Results(value = {
            @Result(property = "wid", column = "wid"),
            @Result(property = "appid", column = "appid"),
            @Result(property = "content", column = "content"),
            @Result(property = "description", column = "description"),
            @Result(property = "workStatus", column = "work_status"),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updateTime", column = "update_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "startDate", column = "start_date", jdbcType = JdbcType.DATE),
            @Result(property = "endDate", column = "end_date", jdbcType = JdbcType.DATE),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "updateBy", column = "update_by")
    })
    @Select("select * from work " +
            "where work_status  < 4 " +
            "and create_time > #{startDate,jdbcType=TIMESTAMP}" +
            "and create_time < #{endDate,jdbcType=TIMESTAMP}")
    List<WorkEntity> getAllWorksByEntity(WorkEntity workEntity);

}
