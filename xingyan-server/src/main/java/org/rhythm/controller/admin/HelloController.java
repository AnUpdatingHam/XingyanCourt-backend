package org.rhythm.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.rhythm.entity.User;
import org.rhythm.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.Enumeration;

//只是一个用于测试是否连通的controller
@CrossOrigin
@RequestMapping("/admin/hello")
@Tag(name = "测试接口", description = "包括最基础的测试方法")
@RestController
@Slf4j
public class HelloController {

    @Operation(summary = "测试请求头")
    @GetMapping("/headers")
    public String printHeaders(@RequestHeader HttpHeaders headers, HttpServletRequest request) {
        // 或者使用 HttpServletRequest 对象获取请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName + ": " + headerValue);
        }

        return "Headers printed in the console.";
    }

    @Operation(summary = "测试hello")
    @RequestMapping
    public String hello(){
        System.out.println("hello");
        return "hello";
    }

    @Operation(summary = "测试返回实体类")
    @GetMapping("/user")
    public UserLoginVO helloUser(){
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(66L)
                .username("我是响应VO的用户名")
                .token("我是token : %!@$ADERQ!@#FAF(^_^)")
                .build();
        System.out.println("收到前端Get请求，返回VO对象");
        return userLoginVO;
    }

    @Operation(summary = "测试post请求")
    @PostMapping("/post")
    public UserLoginVO postHelloUser(@RequestBody User user){
        System.out.println("前端发送的user:" + user);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(66L)
                .username("我是响应VO的用户名")
                .token("我是token : %!@$ADERQ!@#FAF(^_^)")
                .build();
        System.out.println("收到前端Get请求，返回VO对象");
        return userLoginVO;
    }

    @Operation(summary = "测试put请求")
    @PutMapping("/put")
    public UserLoginVO putHelloUser(@RequestBody User user){
        System.out.println("前端发送的user:" + user);
        

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(66L)
                .username("我是响应VO的用户名")
                .token("我是token : %!@$ADERQ!@#FAF(^_^)")
                .build();
        System.out.println("收到前端Get请求，返回VO对象");
        return userLoginVO;
    }

    @Operation(summary = "测试abc")
    @RequestMapping("/abc")
    public String abc(){
        System.out.println("efg");
        return "efg";
    }

}
