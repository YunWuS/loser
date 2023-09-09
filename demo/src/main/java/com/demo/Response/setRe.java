package com.demo.Response;

public class setRe {
    public  static response setSu(Object data){
        return new response(2000,"success",data);
    }
    public static response setFail(Object data){
        return new response(4001,"failure",data);
    }
    public static response setAuthFail(Object data){
        return new response(4002,"lose auth",data);
    }
    public static response setBad(Object data){//垃圾帖子
        return  new response(4003,"Bad post",data);
    }
    public  static  response setUserNull(Object data){
        return  new response(4004,"userNull",data);
    }
}
