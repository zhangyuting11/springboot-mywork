package com.example.demo.controller;

import com.example.demo.Callback;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by v_aytzhang on 2017/8/3.
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    @RequestMapping(value = "/callback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Callback> callback(HttpServletRequest request) {
        System.out.print("=-----------------"+request.getQueryString());
        Callback callback = new Callback();
        callback.setCode(0);
        return ResponseEntity.ok(callback);
//        try {
//            StringBuilder sb = new StringBuilder();
//            BufferedReader reader = request.getReader();
//            try {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line).append('\n');
//                }
//            } finally {
//                reader.close();
//            }
//
//            System.out.print(sb.toString());
//
//            Callback callback = new Callback();
//            callback.setCode(0);
//            return ResponseEntity.ok(callback);
//
//        } catch (Exception io) {
//
//            Callback callback = new Callback();
//            callback.setCode(-1);
//            return new ResponseEntity<Callback>(callback, HttpStatus.INTERNAL_SERVER_ERROR);
//        }

    }
}
