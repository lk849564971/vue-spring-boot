package com.spring.boot.service;

import com.spring.boot.dao.CommodityDAO;
import com.spring.boot.bean.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommodityService {

    @Autowired
    CommodityDAO commodityDAO;

    //查找所有商品
    public List<Commodity> findAll() {
        return commodityDAO.findAll();
    }

    //查找我的商品
    public List<Commodity> findByUno(int uno) {
        return commodityDAO.findByUno(uno);
    }

    //添加商品
    public void addCommodity(Commodity good) {
        commodityDAO.save(good);
    }

    //删除商品
    public void deleteByCno(int goodid) {
        commodityDAO.deleteById(goodid);
    }

    //查找我的订单
    public List<Commodity> findByBuyerno(int buyerno) {
        return commodityDAO.findByBuyerno(buyerno);
    }

    //购买商品
    public void buyCommodity(int cno,int uno) {
        List<Commodity> commodityList = commodityDAO.findByCno(cno);
        Commodity commodity = commodityList.get(0);
        commodity.setCstate(1);
        commodity.setBuyerno(uno);
        commodityDAO.save(commodity);
    }

    //确认收货
    public void dealCommodity(int cno) {
        List<Commodity> commodityList = commodityDAO.findByCno(cno);
        Commodity commodity = commodityList.get(0);
        commodity.setCstate(2);
        commodityDAO.save(commodity);
    }

    //退款
    public void fundCommodity(int cno) {
        List<Commodity> commodityList = commodityDAO.findByCno(cno);
        Commodity commodity = commodityList.get(0);
        commodity.setCstate(0);
        commodity.setBuyerno(-1);
        commodityDAO.save(commodity);
    }
}
