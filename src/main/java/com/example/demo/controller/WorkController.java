package com.example.demo.controller;

import com.example.demo.entity.Work;
import com.example.demo.entity.WorkEntity;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.mapper.WorkMapper;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.WorkResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by v_aytzhang on 2017/8/22.
 */
@Controller
public class WorkController {
    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @RequestMapping(value = "/work", method = RequestMethod.POST)
    public String addWork(@ModelAttribute("work") Work work) {
        WorkEntity workEntity = new WorkEntity();
        workEntity.setBizid(work.getBizid());
        workEntity.setContent(work.getContent());
        workEntity.setDescription(work.getDescription());
        workEntity.setStartDate(strToDateLong(work.getStartDate()));
        workEntity.setEndDate(strToDateLong(work.getEndDate()));
        workEntity.setWorkStatus(work.getWorkStatus());
        workEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        workMapper.addWork(workEntity);
        return "index";
    }

    @RequestMapping(value = "/addwork", method = RequestMethod.GET)
    public String addWork(ModelMap map) {
        map.addAttribute("work", new Work());
        return "addwork";
    }

    @RequestMapping("/work")
    public String getAllWorks(ModelMap map) {
        List<WorkEntity> workEntityList = workMapper.getAllWorks();
        map.addAttribute("work", workEntityList);
        map.addAttribute("getwork", new WorkEntity());
        return "work";
    }

    @RequestMapping("/getwork")
    public String getWorks(ModelMap map,
                           @ModelAttribute("getwork") WorkEntity workEntity) {
        WorkEntity work = new WorkEntity();
        if (!workEntity.getStartTime().isEmpty() && !workEntity.getEndTime().isEmpty()) {
            work.setStartDate(strToDateLong(workEntity.getStartTime()));
            work.setEndDate(strToDateLong(workEntity.getEndTime()));
        } else {
            work.setStartDate(strToDateLong("1970-01-01"));
            work.setEndDate(strToDateLong("2036-01-01"));
        }
        List<WorkEntity> workEntityList = new ArrayList<>();
        if (workEntity.getWorkStatus() != -1) {
            work.setWorkStatus(workEntity.getWorkStatus());
            workEntityList = workMapper.getWorksByEntity(work);
        } else {
            workEntityList = workMapper.getAllWorksByEntity(work);
        }
        map.addAttribute("work", workEntityList);
        return "work";
    }

    @RequestMapping("/work/{wid}")
    public String getWorksByStatusAndBizid(ModelMap map,
                                           @PathVariable int wid) {
        WorkEntity info = workMapper.getWorksByPrimarykey(wid);
        map.addAttribute("work", info);
        return "updatework";
    }

    @RequestMapping(value = "/updatework", method = RequestMethod.GET)
    public String updateWork(@ModelAttribute("work") Work work) {
        WorkEntity workEntity = new WorkEntity();
        workEntity.setWid(work.getWid());
        workEntity.setBizid(work.getBizid());
        workEntity.setContent(work.getContent());
        workEntity.setDescription(work.getDescription());
        workEntity.setWorkStatus(work.getWorkStatus());
        workMapper.updateObject(workEntity);
        return "index";
    }


    public static Date strToDateLong(String dateStr) {
        Date date = new Date();
        //注意format的格式要与日期String的格式相匹配
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(dateStr);
            System.out.println(date.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


}
