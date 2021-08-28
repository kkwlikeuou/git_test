package cn.kkwli.travel.service.impl;

import cn.kkwli.travel.dao.RouteDao;
import cn.kkwli.travel.dao.RouteImgDao;
import cn.kkwli.travel.dao.impl.RouteDaoImpl;
import cn.kkwli.travel.dao.impl.RouteImgDaoImpl;
import cn.kkwli.travel.dao.SellDao;
import cn.kkwli.travel.dao.impl.SellDaoImpl;
import cn.kkwli.travel.domain.PageBean;
import cn.kkwli.travel.domain.Route;
import cn.kkwli.travel.domain.Seller;
import cn.kkwli.travel.service.RouteService;

public class RouteServiceImpl implements RouteService {
    private final RouteDao dao = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int uid, int currentPage, int pageSize, String rname) {
        PageBean<Route> page = new PageBean<>();
        page.setList(dao.findByPage(cid, uid, currentPage, pageSize, rname));

        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        int totalCount = dao.findTotalCount(cid, uid, rname);
        page.setTotalCount(totalCount);
        page.setTotalPage(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);


        return page;
    }

    @Override
    public Route findByRid(int rid) {
        Route route = dao.findByRid(rid);
        RouteImgDao routeImgDao = new RouteImgDaoImpl();
        route.setRouteImgList(routeImgDao.findByRid(rid));
        SellDao sellDao = new SellDaoImpl();
        Seller seller = sellDao.findBySid(route.getSid());
        route.setSeller(seller);
        return route;
    }

    @Override
    public void plusCount(int rid) {
        dao.plusCount(rid);
    }

    @Override
    public void subCount(int rid) {
        dao.subCount(rid);
    }

    @Override
    public int findCount(int rid) {
        return dao.findCount(rid);
    }


}
