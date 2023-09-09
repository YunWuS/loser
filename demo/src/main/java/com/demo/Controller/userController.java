package com.demo.Controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.demo.Mapper.Usermappper;
import com.demo.Pojo.post;
import com.demo.Pojo.user;
import com.demo.Pojo.userapply;
import com.demo.Response.response;
import com.demo.Response.setRe;
import com.demo.Service.Postservice;
import com.demo.Service.Userapplyservice;
import com.demo.Service.Userservice;
import com.demo.search.CheckForSpam;
import com.demo.utils.NowTime;
import lombok.extern.slf4j.Slf4j;
import org.jcp.xml.dsig.internal.dom.ApacheOctetStreamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    Userservice userservice;
    @Autowired
    Postservice postservice;

    @Autowired
    Userapplyservice userapplyservice;

    //获取个人信息
    @GetMapping("/userInfo")
    public response getuserbyid(@RequestParam(name = "id")int id){
        log.info("个人信息");
        return setRe.setSu(userservice.selectById(id));
    }
    //更改用户信息
    @PostMapping("/updateInfo")
    public response updateInfo(@RequestBody user user){

        try {
            userservice.updateById(user);
           user usera= new user();
            usera=userservice.selectById(user.getId());
            usera.setPassword(null);
            return setRe.setSu(usera);
        } catch (Exception e) {
            log.error("更新{}用户信息失败",user.getUsername());
            return setRe.setFail(userservice.selectById(user.getId()));
        }
    }
    //1:拾取者 2:丢失者,根据infoid查看贴子
    @GetMapping("/seeList")
    public response seeList(@RequestParam("infoid") int infoid){
        log.info("查看帖子");
        List<post> posts = new ArrayList<>();
        if(infoid==1){
            posts = postservice.selectList(new EntityWrapper<post>().eq("type", "2").orderBy("crscore",true));
        } else if (infoid==2) {
            posts = postservice.selectList(new EntityWrapper<post>().eq("type", "1").orderBy("crscore",true));
        }
        else {
            posts = postservice.selectList(new EntityWrapper<post>().orderBy("crscore",true));
        }
        return setRe.setSu(posts);
    }
    //发布贴子，如果权限小于1则不能发布
    @PostMapping("/pullLose")
    @CheckForSpam
    public response pullLose(@RequestBody post post){

        if(userservice.selectOne(new EntityWrapper<user>().eq("username", post.getUsername())).getAuth()>0){
            log.info("进入发布帖子进程");
            log.info("用户权限{}",userservice.selectOne(new EntityWrapper<user>().eq("username", post.getUsername())).getAuth());
            post.setCrscore(userservice.selectOne(new EntityWrapper<user>().eq("username", post.getUsername())).getCrscore());
          post.setDate(  NowTime.Now());
            postservice.insert(post);
            return setRe.setSu(post);
        }
        else{
            log.info("{}用户处于禁言中",post.getUsername());
            return setRe.setAuthFail(post);
        }

    }
    //申请成为管理员
    @GetMapping("/wantAdmin")
    public response wantAdmin(@RequestParam("id") int id){
        log.info("申请管理员");
        user u = userservice.selectById(id);
        userapplyservice.insert(new userapply(1,u.getUsername(),NowTime.Now(),u.getCrscore()));
        return setRe.setSu(null);
    }
    //通过用户id得到用户姓名，查看已发布贴子
    @GetMapping("/selfPull")
    public response selfPull(@RequestParam(name = "id")int id){
        log.info("查看自己的帖子");
        List<post> posts = postservice.selectList(new EntityWrapper<post>().eq("username", userservice.selectById(id).getUsername()));
        return setRe.setSu(posts);
    }
    //根据贴子id删除贴子
    @GetMapping("/deletePost")
    public response deletePost(@RequestParam(name = "id")int id){
        log.info("删除帖子");
        postservice.deleteById(id);

        return setRe.setSu(null);
    }
    //根据帖子id修改贴子
    @PostMapping("/updatePull")
    public response updatePull(@RequestBody post post){
        log.info("更新帖子");
        postservice.updateById(post);
        return setRe.setSu(post);
    }


}
