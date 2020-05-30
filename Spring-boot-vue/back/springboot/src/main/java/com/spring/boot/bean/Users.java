package com.spring.boot.bean;

import javax.persistence.*;

@Entity //实体类
@Table(name = "user") //表名 user
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //标注主键，且为自增长型
    @Column(name = "uno")
    private int uno;

    @Column(name = "uname")
    private String uname;

    @Column(name = "upwd")
    private String upwd;

    public int getUno() { return uno; }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() { return upwd; }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }


}
