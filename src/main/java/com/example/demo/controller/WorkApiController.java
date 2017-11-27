package com.example.demo.controller;

import com.example.demo.mapper.CustomerMapper;
import com.example.demo.response.BaseResponse;
import com.example.demo.entity.WorkEntity;
import com.example.demo.mapper.WorkMapper;
import com.example.demo.response.WorkResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by v_aytzhang on 2017/8/11.
 */
@EnableWebMvc
@RestController
@RequestMapping("/v1/api")
public class WorkApiController {

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @ApiOperation(value = "获取工作任务列表")
    @RequestMapping(value = "/work", method = RequestMethod.GET)
    public ResponseEntity<BaseResponse> getAllWorks() {
        BaseResponse base = new BaseResponse();
        try {
            List<WorkEntity> workEntityList = workMapper.getAllWorks();
            List<WorkResponse> workResponseList = new ArrayList<>();
            for (WorkEntity info : workEntityList) {
                WorkResponse workResponse = new WorkResponse();
                workResponse.setCustomer(customerMapper.selectCustomer(info.getBizid()));
                workResponse.setContent(info.getContent());
                workResponse.setCreateBy(info.getCreateBy());
                workResponse.setCreateTime(info.getCreateTime());
                workResponse.setDescription(info.getDescription());
                workResponse.setEndDate(info.getEndDate());
                workResponse.setStartDate(info.getStartDate());
                workResponse.setUpdateBy(info.getUpdateBy());
                workResponse.setUpdateTime(info.getUpdateTime());
                workResponse.setWid(info.getWid());
                workResponse.setWorkStatus(getStatus(info.getWorkStatus()));
                workResponseList.add(workResponse);
            }
            base.setCode(200);
            if (workEntityList.size() > 0)
                base.setObject(workResponseList);
            else
                base.setMessage("暂时没有更多数据");
            return ResponseEntity.ok(base);
        } catch (Exception e) {
            base.setMessage(e.getMessage());
            base.setCode(500);
            return new ResponseEntity<BaseResponse>(base, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "获取不同状态的工作任务列表")
    @RequestMapping(value = "/work/{workStatus}", method = RequestMethod.GET)
    public ResponseEntity<BaseResponse> getWorks(@PathVariable int workStatus) {
        BaseResponse base = new BaseResponse();
        if (workStatus > 3 || workStatus < 0) {
            return new ResponseEntity<BaseResponse>(base, HttpStatus.UNAUTHORIZED);
        }
        try {
            List<WorkEntity> workEntityList = workMapper.getWorks(workStatus);
            List<WorkResponse> workResponseList = new ArrayList<>();
            for (WorkEntity info : workEntityList) {
                WorkResponse workResponse = new WorkResponse();
                workResponse.setCustomer(customerMapper.selectCustomer(info.getBizid()));
                workResponse.setContent(info.getContent());
                workResponse.setCreateBy(info.getCreateBy());
                workResponse.setCreateTime(info.getCreateTime());
                workResponse.setDescription(info.getDescription());
                workResponse.setEndDate(info.getEndDate());
                workResponse.setStartDate(info.getStartDate());
                workResponse.setUpdateBy(info.getUpdateBy());
                workResponse.setUpdateTime(info.getUpdateTime());
                workResponse.setWid(info.getWid());
                workResponse.setWorkStatus(getStatus(info.getWorkStatus()));
                workResponseList.add(workResponse);
            }
            base.setCode(200);
            if (workEntityList.size() > 0)
                base.setObject(workResponseList);
            else
                base.setMessage("暂时没有更多数据");
            return ResponseEntity.ok(base);
        } catch (Exception e) {
            base.setMessage(e.getMessage());
            base.setCode(500);
            return new ResponseEntity<BaseResponse>(base, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @ApiOperation(value = "添加新的工作任务")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "appid", value = "客户Appid", dataType = "String"),
//            @ApiImplicitParam(name = "content", value = "工作任务内容", dataType = "String"),
//            @ApiImplicitParam(name = "description", value = "工作任务描述", dataType = "String"),
//            @ApiImplicitParam(name = "status", value = "0未开始,1进行中,2已完成,3再次开启", dataType = "Integer")})
    @RequestMapping(value = "/work", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> addWork(@RequestHeader(value = "auth") String auth,
                                                @RequestParam(value = "bizid") String bizid,
                                                @RequestParam(value = "content") String content,
                                                @RequestParam(value = "description") String description,
                                                @RequestParam(value = "startDate") Date startDate,
                                                @RequestParam(value = "endDate") Date endDate,
                                                @RequestParam(value = "status") Integer status) {
        BaseResponse base = new BaseResponse();
        if (auth == null || !auth.equals("v_aytzhang")) {
            return new ResponseEntity<BaseResponse>(base, HttpStatus.UNAUTHORIZED);
        }
        WorkEntity workEntity = new WorkEntity();
        workEntity.setBizid(bizid);
        workEntity.setContent(content);
        workEntity.setDescription(description);
        workEntity.setStartDate(startDate);
        workEntity.setEndDate(endDate);
        workEntity.setCreateBy(auth);
        workEntity.setWorkStatus(status);
        workEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
            if (workMapper.addWork(workEntity) == 1) {
                base.setCode(201);
                base.setMessage("添加成功");
                return new ResponseEntity<BaseResponse>(base, HttpStatus.CREATED);
            } else {
                base.setCode(400);
                base.setMessage("添加失败！");
                return new ResponseEntity<BaseResponse>(base, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            base.setMessage(e.getMessage());
            base.setCode(500);
            return new ResponseEntity<BaseResponse>(base, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "修改工作任务状态")
    @RequestMapping(value = "/work", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> updateStatus(@RequestHeader(value = "auth") String auth, @RequestParam int workStatus, @RequestParam int wid) {
        BaseResponse base = new BaseResponse();
        try {
            if (auth == null || !auth.equals("v_aytzhang")) {
                return new ResponseEntity<BaseResponse>(base, HttpStatus.UNAUTHORIZED);
            }
            if ((workStatus > 3 || workStatus < 0) || workMapper.updateWork(workStatus, wid, new Timestamp(System.currentTimeMillis()), auth) != 1) {
                base.setCode(400);
                base.setMessage("修改失败！");
                return new ResponseEntity<BaseResponse>(base, HttpStatus.BAD_REQUEST);
            } else {
                base.setCode(201);
                base.setMessage("修改成功！");
                return ResponseEntity.ok(base);
            }

        } catch (Exception e) {
            base.setMessage(e.getMessage());
            base.setCode(500);
            return new ResponseEntity<BaseResponse>(base, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private String getStatus(int status) {
        switch (status) {
            case 0:
                return "未开始";
            case 1:
                return "进行中";
            case 2:
                return "已完成";
        }
        return "再次开始";
    }


}
