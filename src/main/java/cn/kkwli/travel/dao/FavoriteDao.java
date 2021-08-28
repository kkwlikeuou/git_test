package cn.kkwli.travel.dao;

import cn.kkwli.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {
    Integer findExist(int rid, int uid);

    boolean addOne(int rid, int uid);

    boolean removeOne(int rid, int uid);

    List<Route> findRoutes(int uid, int start , int pageSize);
}
