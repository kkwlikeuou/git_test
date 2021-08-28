package cn.kkwli.travel.dao.impl;

import cn.kkwli.travel.dao.RouteImgDao;
import cn.kkwli.travel.domain.RouteImg;
import cn.kkwli.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private final JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSource());
    private String sql;

    @Override
    public List<RouteImg> findByRid(int rid) {
        sql = "SELECT * FROM travel.tab_route_img WHERE rid = ?";
        return jt.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
    }
}
