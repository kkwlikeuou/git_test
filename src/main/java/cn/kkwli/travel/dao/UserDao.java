package cn.kkwli.travel.dao;

import cn.kkwli.travel.domain.User;

public interface UserDao {
    User findByUsername(String username);

    User findByCode(String code);

    void save(User user);

    void updateStatus(User user);

    User findByUsername(String username,String password);
}
