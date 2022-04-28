package com.youjh.Controller;


import com.youjh.handler.RequestParam;
import com.youjh.requestMap.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {
    @RequestMapping("/test")
    public  String test(@RequestParam("name") String name){
        System.out.println("-----------RequestMapping");
        return "test";
    }

    @RequestMapping("/test1")
    public  String test1(){
        System.out.println("-----------RequestMapping");
        return "test1";
    }
}
