package com.demo.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class post {
    private int id;
    private String username;
    private String tel;
    private String describe;
    private String Picaddr;
    private String date;
    private String type;
    private int crscore;
}
