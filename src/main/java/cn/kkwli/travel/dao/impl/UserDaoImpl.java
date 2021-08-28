package cn.kkwli.travel.dao.impl;

import cn.kkwli.travel.dao.UserDao;
import cn.kkwli.travel.domain.User;
import cn.kkwli.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSource());
    private String sql;

    public User findByUsername(String username) {
        User user = null;
        try {
            sql = "SELECT * FROM travel.tab_user WHERE username = ?";
            user = jt.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);

        } catch (DataAccessException ignored) {

        }
        return user;
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            sql = "SELECT * FROM travel.tab_user WHERE code = ?";
            user = jt.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        } catch (DataAccessException ignored) {

        }
        return user;
    }

    public void save(User user) {
        sql = "INSERT INTO travel.tab_user (username, password, name, birthday, sex, telephone, email,status,code) Values (?,?,?,?,?,?,?,?,?)";
        jt.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    @Override
    public void updateStatus(User user) {
        sql = "UPDATE travel.tab_user SET status='Y' WHERE uid=?";
        jt.update(sql, user.getUid());
    }

    @Override
    public User findByUsername(String username, String password) {
        User user = null;

        try {
            sql = "SELECT * FROM travel.tab_user WHERE username=? AND password=?";
            user = jt.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException ignored) {

        }
        return user;
    }


}
