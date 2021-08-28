package cn.kkwli.travel.service.impl;

import cn.kkwli.travel.dao.CategoryDao;
import cn.kkwli.travel.dao.impl.CategoryDaoImpl;
import cn.kkwli.travel.domain.Category;
import cn.kkwli.travel.service.CategoryService;
import cn.kkwli.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> findAll() {
        List<Category> cs;
        Jedis jedis = JedisUtil.getJedis();
//        Set<String> categories = jedis.zrange("category", 0, -1);
        Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);
        if (categories == null || categories.size() == 0) {
            CategoryDao dao = new CategoryDaoImpl();
            cs = dao.findAll();
            for (Category c : cs) {
                jedis.zadd("category", c.getCid(), c.getCname());
            }
        } else {
            cs = new ArrayList<>();
            for (Tuple tuple : categories) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                cs.add(category);
            }
        }
        return cs;

    }
}
