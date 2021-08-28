package cn.kkwli.travel.dao.impl;

import cn.kkwli.travel.dao.FavoriteDao;
import cn.kkwli.travel.domain.Route;
import cn.kkwli.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    private String sql;

    @Override
    public Integer findExist(int rid, int uid) {
        sql = "SELECT COUNT(*) FROM travel.tab_favorite WHERE rid = ? AND  uid = ? LIMIT 1";
        return template.queryForObject(sql, Integer.class, rid, uid);
    }

    @Override
    public boolean addOne(int rid, int uid) {
        Date date = new Date();
        SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd");
        String format = myDate.format(date);
        sql = "INSERT INTO travel.tab_favorite (rid, date, uid) VALUES (?, ?, ?)";
        try {
            template.update(sql, rid, format, uid);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean removeOne(int rid, int uid) {
        try {
            sql = "DELETE FROM travel.tab_favorite WHERE rid = ? AND uid = ?";
            template.update(sql, rid, uid);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public List<Route> findRoutes(int uid , int start , int pageSize) {
        sql = "SELECT * FROM travel.tab_favorite t1,travel.tab_route t2 WHERE t1.uid = ? AND t1.rid = t2.rid LIMIT ? , ?";
        System.out.println(sql);
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), uid , start ,pageSize);
    }
}
