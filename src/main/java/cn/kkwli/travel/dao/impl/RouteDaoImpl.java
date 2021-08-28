package cn.kkwli.travel.dao.impl;

import cn.kkwli.travel.dao.RouteDao;
import cn.kkwli.travel.domain.Route;
import cn.kkwli.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private final JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSource());
    private String sql;

    @Override
    public Integer findTotalCount(int cid, int uid, String rname) {
        sql = "SELECT COUNT(*) FROM travel.tab_route t1 WHERE 1 = 1 ";
        List params = new ArrayList();
        StringBuilder sb = new StringBuilder(sql);
        if (cid != 0) {
            sb.append(" AND cid = ? ");
            params.add(cid);
        }
        if (uid != 0) {
            sb = new StringBuilder(sb.toString().split("W")[0]);
            sb.append(", travel.tab_favorite t2 WHERE uid = ? AND t1.rid = t2.rid ");
            params.add(uid);

        }
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" AND rname LIKE ? ");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();
        System.out.println(sql);
        System.out.println(params);
        return jt.queryForObject(sql, Integer.class, params.toArray());

    }

    @Override
    public List<Route> findByPage(int cid, int uid, int currentPage, int pageSize, String rname) {
        sql = "SELECT * FROM travel.tab_route t1 WHERE 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        pageSize = 5;
        if (cid != 0) {
            sb.append(" AND cid = ? ");
            params.add(cid);
        }
        if (uid != 0) {
            pageSize = 16;
            sb = new StringBuilder(sb.toString().split("W")[0]);
            sb.append(", travel.tab_favorite t2 WHERE uid = ? AND t1.rid = t2.rid ");
            params.add(uid);

        }
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" AND rname LIKE ? ");
            params.add("%" + rname + "%");
        }
        sb.append(" LIMIT ? , ?");
        params.add((currentPage - 1) * pageSize);
        params.add(pageSize);
        sql = sb.toString();
        System.out.println(sql);
        System.out.println(params);
        return jt.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }

    @Override
    public Route findByRid(int rid) {
        sql = "SELECT * FROM travel.tab_route WHERE rid = ?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
    }

    @Override
    public void plusCount(int rid) {
        if (rid != 0) {
            sql = "UPDATE travel.tab_route SET count = count + 1 WHERE rid = ?";
            jt.update(sql, rid);
        }
    }

    @Override
    public void subCount(int rid) {
        if (rid != 0) {
            sql = "UPDATE travel.tab_route SET count = count - 1 WHERE rid = ?";
            jt.update(sql, rid);
        }
    }

    @Override
    public Integer findCount(int rid) {
        sql = "SELECT count FROM travel.tab_route WHERE rid = ?";
        return jt.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public List<Route> findFavouriteByPage(int uid, int start, int pageSize) {
        sql = "SELECT * FROM travel.tab_route t1,travel.tab_favorite t2 WHERE t2.uid = ? AND t1.rid = t2.rid LIMIT ? , ?";

        return jt.query(sql, new BeanPropertyRowMapper<>(Route.class), uid, start, pageSize);
    }

    @Override
    public Integer findFavouriteCount(int uid) {
        sql = "SELECT COUNT(*) FROM travel.tab_favorite WHERE uid = ?";
        System.out.println(sql);
        return jt.queryForObject(sql, Integer.class, uid);
    }
}
