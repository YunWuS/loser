package com.demo.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class user {
    private  int id;
    private  int Infoid;
    private String username;
    private String password;
    private String tel;
    private int crscore;
    private int auth;
    private int calmday;
}
