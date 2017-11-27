package com.example.demo.controller;

import com.example.demo.response.BaseResponse;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.mapper.CustomerMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by v_aytzhang on 2017/8/16.
 */
@RestController
@RequestMapping("/v1/api")
public class CustomerApiController {

    @Autowired
    CustomerMapper customerMapper;

    @ApiOperation(value = "添加新的用户")
    @RequestMapping(value = "/addcustomer", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> addCustomer(@RequestBody CustomerEntity customerEntity) {
        BaseResponse base = new BaseResponse();
        try {
            if (customerMapper.selectAppid(customerEntity.getAppid()) != null) {
                base.setCode(200);
                base.setMessage("已有该客户信息");
                return ResponseEntity.ok(base);
            }
            customerMapper.addCustomer(customerEntity);
            base.setCode(201);
            base.setMessage("添加成功");
            return new ResponseEntity<BaseResponse>(base, HttpStatus.CREATED);
        } catch (Exception e) {
            base.setMessage(e.getMessage());
            base.setCode(500);
            return new ResponseEntity<BaseResponse>(base, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
