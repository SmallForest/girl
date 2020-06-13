package com.imooc.repository;

import com.imooc.domain.Girl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 廖师兄
 * 2016-11-03 23:17
 */
public interface GirlRepository extends JpaRepository<Girl, Integer> {

    //通过年龄来查询
    public List<Girl> findByAge(Integer age);

    //通过年龄区间查询 文档查询 https://www.cntofu.com/book/88/Working-with-Spring-Data-Repositories/defining-query-methods.md
    public List<Girl> findGirlsByAgeBetween(Integer min,Integer max);
}
