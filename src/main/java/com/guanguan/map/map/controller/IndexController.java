package com.guanguan.map.map.controller;

import com.alibaba.fastjson.JSON;
import com.guanguan.map.map.domain.DataDomain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @program: map
 * @description: 主页处理类 主要职责从缓冲区获取数据并等待前台提取
 * @author: 杨永平
 * @create: 2018年01月23日 15:10
 **/
@RestController
public class IndexController {
    @GetMapping("/send")
    public String send(){
        System.out.println("send");
        new Thread(()->{
            long x = 114050564,y=22541352;
            while (true){
                DataDomain dataDomain = new DataDomain();
                dataDomain.setAcceleration("setAcceleration");
                dataDomain.setAngularAcc("setAngularAcc");
                dataDomain.setHumidity("setHumidity");
                x+=100;
                y+=100;
                dataDomain.setLongitude(new BigDecimal(x).divide(new BigDecimal(1000000)).toString());
                dataDomain.setLatitude(new BigDecimal(y).divide(new BigDecimal(1000000)).toString());
                dataDomain.setObliquity("setObliquity");
                dataDomain.setTemperature("setTemperature");
                try {
                    IndexWebSocket.sendInfo(JSON.toJSONString(dataDomain));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
        return "1";
    }
}
