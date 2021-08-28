package cn.kkwli.travel.dao.impl;

import cn.kkwli.travel.dao.SellDao;
import cn.kkwli.travel.domain.Seller;
import cn.kkwli.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellDaoImpl implements SellDao {
    private final JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSource());
    private String sql;

    @Override
    public Seller findBySid(int sid) {
        sql = "SELECT * FROM travel.tab_seller WHERE sid = ?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
    }
}
