package cn.kkwli.travel.dao;

import cn.kkwli.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    List<RouteImg> findByRid(int rid);
}
