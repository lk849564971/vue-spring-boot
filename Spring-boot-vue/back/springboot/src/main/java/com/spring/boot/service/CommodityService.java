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

    public List<Commodity> findAll() {
        return commodityDAO.findAll();
    }

    public List<Commodity> findByUno(int uno) {
        return commodityDAO.findByUno(uno);
    }

    public void addCommodity(Commodity good) {
        commodityDAO.save(good);
    }

    public void deleteByCno(int goodid) {
        commodityDAO.deleteById(goodid);
    }

    public List<Commodity> findByBuyerno(int buyerno) {
        return commodityDAO.findByBuyerno(buyerno);
    }

    public void buyCommodity(int cno,int uno) {
        List<Commodity> commodityList = commodityDAO.findByCno(cno);
        Commodity commodity = commodityList.get(0);
        commodity.setCstate(1);
        commodity.setBuyerno(uno);
        commodityDAO.save(commodity);
    }

    public void dealCommodity(int cno) {
        List<Commodity> commodityList = commodityDAO.findByCno(cno);
        Commodity commodity = commodityList.get(0);
        commodity.setCstate(2);
        commodityDAO.save(commodity);
    }

    public void fundCommodity(int cno) {
        List<Commodity> commodityList = commodityDAO.findByCno(cno);
        Commodity commodity = commodityList.get(0);
        commodity.setCstate(0);
        commodity.setBuyerno(-1);
        commodityDAO.save(commodity);
    }
}
