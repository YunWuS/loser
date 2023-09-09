package com.demo;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.Pojo.post;
import com.demo.Pojo.user;
import com.demo.Pojo.userapply;
import com.demo.Service.Postservice;
import com.demo.Service.Userapplyservice;
import com.demo.Service.Userservice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    Userservice userservice;
    @Autowired
    Userapplyservice userapplyservice;
    @Test
    void contextLoads() {

        user user=new user();
        user.setAuth(3);
        user.setPassword("dsadas");
        user.setUsername("niubi");
        user.setInfoid(2);
        user.setTel("19996623821");
        user.setCrscore(100);
        userservice.insert(user);


    }
    @Test
    void test(){

        for (int i = 0; i <100 ; i++) {
            user user = new user();
            user.setAuth(1);
            user.setPassword("dsadas");
            user.setUsername("坤坤"+i);
            user.setInfoid(2);
            user.setTel("19996623821");
            user.setCrscore(100);
            userservice.insert(user);
        }
    }
    @Test
    void test1(){

        for (int i = 1; i <10 ; i++) {
            LocalDate date = LocalDate.now(); // get the current date

            userapply userapply = new userapply();
            userapply.setId(i);
            userapply.setUsername("坤坤"+i);
            userapply.setDate(date);
            userapply.setCrscore(100);
            userapplyservice.insert(userapply);

        }

    }
    //雪白色入耳式蓝牙耳机，一串带有中国结的钥匙
    @Autowired
    Postservice postservice;
    @Test
    void Te(){
        for (int i = 0; i < 50; i++) {
            postservice.insert(new post(1,"坤坤"+i,"19996623821","雪白色入耳式蓝牙耳机","","2023-07-06 15:00:00","2",100));
        }

    }

}
