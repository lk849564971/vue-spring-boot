package com.spring.boot.controller;

import com.spring.boot.bean.Commodity;
import com.spring.boot.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController//直接返回JSON数据
@RequestMapping("/api")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    //所有商品
    @RequestMapping("/allCommodity")
    public List<Commodity> allCommodity() {
        return commodityService.findAll();
    }

    //查看订单
    @RequestMapping("/myBuyCommodity")
    public List<Commodity> myBuyCommodity(@RequestParam(value = "uno") String uno) {
        return commodityService.findByBuyerno(Integer.parseInt(uno));
    }

    //我的商品
    @RequestMapping("/myCommodity")
    public List<Commodity> myCommodity(@RequestParam(value = "uno") String uno) {
        return commodityService.findByUno(Integer.parseInt(uno));
    }

    //添加商品
    @RequestMapping("/addCommodity")
    public Boolean addCommodity(@RequestParam(value = "uno") String uno,
                           @RequestParam(value = "cname") String cname,
                           @RequestParam(value = "cprice") float cprice,
                           @RequestParam(value = "cinformation") String cinformation) throws Exception {
        try {
            Commodity commodity = new Commodity();
            commodity.setUno(Integer.parseInt(uno));
            commodity.setCname(cname);
            commodity.setCprice(cprice);
            commodity.setCinformation(cinformation);
            commodity.setCstate(0);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            commodity.setCtime(df.format(new Date()));
            commodity.setBuyerno(-1);
            commodityService.addCommodity(commodity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //下架商品，删除记录
    @RequestMapping("/deleteCommodity")
    public Boolean deleteCommodity(@RequestParam(value = "cno") String cno){
        int no = Integer.parseInt(cno);
        try{
            commodityService.deleteByCno(no);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //购买
    @RequestMapping("/buyCommodity")
    public Boolean buyCommodity(@RequestParam(value = "cno")String cno,@RequestParam(value = "uno")String uno){
        int no = Integer.parseInt(cno);
        int buyerno = Integer.parseInt(uno);
        try{
            commodityService.buyCommodity(no,buyerno);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //收货
    @RequestMapping("/dealCommodity")
    public Boolean dealCommodity(@RequestParam(value = "cno")String cno){
        int no = Integer.parseInt(cno);

        try{
            commodityService.dealCommodity(no);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //退款
    @RequestMapping("/fundCommodity")
    public Boolean fundCommodity(@RequestParam(value = "cno")String cno){
        int no = Integer.parseInt(cno);

        try{
            commodityService.fundCommodity(no);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


}
