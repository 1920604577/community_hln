//package com.gsxy.core;
//
//import com.gsxy.core.pojo.vo.ResponseVo;
//import com.gsxy.core.service.SystemService;
//import com.gsxy.core.util.JwtUtil;
//import com.gsxy.core.util.ThreadLocalUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class CoreApplicationTests {
//
//    @Autowired
//    private SystemService systemService;
//
//    @Test
//    void contextLoads() {
//        ResponseVo admin = systemService.isAdmin("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJxZHNxZHEiLCJpYXQiOjE2OTgyMTU3NzQsInN1YiI6ImFzZHF3c2RxcyIsImV4cCI6MTY5ODgyMDU3NCwiaWQiOiI2In0.WeFkC7jiu7ibreE_MYJIsjsTOYx3HES43KH0CA_tfA8", 2);
//        System.out.println(ThreadLocalUtil.mapThreadLocal.get());
//    }
//
//
//}
