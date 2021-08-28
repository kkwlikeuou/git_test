package cn.kkwli.travel.dao.impl;

import cn.kkwli.travel.dao.CategoryDao;
import cn.kkwli.travel.domain.Category;
import cn.kkwli.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
public class CategoryDaoImpl implements CategoryDao {
    private final JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSource());
    private String sql;

    @Override
    public List<Category> findAll() {
        sql = "SELECT * FROM travel.tab_category";
        return jt.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
}
