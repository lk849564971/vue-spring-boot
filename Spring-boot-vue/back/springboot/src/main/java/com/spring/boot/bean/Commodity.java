package com.spring.boot.bean;

import javax.persistence.*;

@Entity//实体类
@Table(name = "commodity")//表名 commodity
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//标注主键，且为自增长型
    @Column(name = "cno")
    private int cno;

    @Column(name = "uno")
    private int uno;

    @Column(name = "cname")
    private String cname;

    @Column(name = "cprice")
    private float cprice;

    @Column(name = "cinformation")
    private String cinformation;

    @Column(name = "cstate")
    private int cstate;
    //未卖是0,已付款为1,交易完成2

    @Column(name = "ctime")
    private String ctime;

    @Column(name = "buyerno")
    private int buyerno;

    public int getCno() {
        return cno;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public float getCprice() {
        return cprice;
    }

    public void setCprice(float cprice) {
        this.cprice = cprice;
    }

    public String getCinformation() {
        return cinformation;
    }

    public void setCinformation(String cinformation) {
        this.cinformation = cinformation;
    }

    public int getCstate() {
        return cstate;
    }

    public void setCstate(int cstate) {
        this.cstate = cstate;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getBuyerno() {
        return buyerno;
    }

    public void setBuyerno(int buyerno) {
        this.buyerno = buyerno;
    }
}
