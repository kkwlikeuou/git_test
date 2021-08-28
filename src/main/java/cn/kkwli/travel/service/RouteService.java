package cn.kkwli.travel.service;

import cn.kkwli.travel.domain.PageBean;
import cn.kkwli.travel.domain.Route;



public interface RouteService {
    PageBean<Route> pageQuery(int cid, int uid, int currentPage, int pageSize, String rname);

    Route findByRid(int rid);

    void plusCount(int rid);

    void subCount(int rid);

    int findCount(int rid);
}
