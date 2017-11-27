package com.example.demo.controller;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.Work;
import com.example.demo.entity.WorkEntity;
import com.example.demo.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by v_aytzhang on 2017/8/29.
 */
@Controller
public class CustomerController {
    @Autowired
    CustomerMapper customerMapper;

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public String customer(@ModelAttribute("content") CustomerEntity content) {
        System.out.print(content.getAppid());
        customerMapper.addCustomer(content);
        return "index";
    }

    @RequestMapping(value = "/addcustomer", method = RequestMethod.GET)
    public String addcustomer(ModelMap map) {
        map.addAttribute("content", new CustomerEntity());
        return "addcustomer";
    }


}
