package com.spring.boot.dao;

import com.spring.boot.bean.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository 提供各种db操作接口
//泛型<Users,Integer>表示这是个针对Commodity类的DAO，Intger表示主键是Integer类型
public interface CommodityDAO extends JpaRepository<Commodity, Integer> {

    public List<Commodity> findByUno(int uno );

    public List<Commodity> findByCno(int cno );

    public List<Commodity> findByBuyerno(int sellerno);
}
