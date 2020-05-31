package com.spring.boot.dao;

import com.spring.boot.bean.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository 提供各种db操作接口
//泛型<Users,Integer>表示这是个针对Users类的DAO，Intger表示主键是Integer类型
public interface UserDAO extends JpaRepository<Users, Integer> {
    //通过uname查找用户
    public List<Users> findByUname(String uname);
}
