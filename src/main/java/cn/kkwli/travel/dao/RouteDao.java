package cn.kkwli.travel.dao;

import cn.kkwli.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    Integer findTotalCount(int cid, int uid, String rname);

    List<Route> findByPage(int cid, int uid, int start, int pageSize, String rname);

    Route findByRid(int rid);

    void plusCount(int rid);

    void subCount(int rid);

    Integer findCount(int rid);

    List<Route> findFavouriteByPage(int uid, int start, int pageSize);

    Integer findFavouriteCount(int uid);
}
