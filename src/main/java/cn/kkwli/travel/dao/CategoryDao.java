package cn.kkwli.travel.dao;

import cn.kkwli.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
}
