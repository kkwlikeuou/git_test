package cn.kkwli.travel.service;

import cn.kkwli.travel.domain.PageBean;
import cn.kkwli.travel.domain.Route;

public interface FavouriteService {
    boolean findOne(int rid, int uid);

    boolean addOne(int rid, int uid);

    boolean removeOne(int rid, int uid);

}
