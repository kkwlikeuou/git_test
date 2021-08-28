package cn.kkwli.travel.service.impl;

import cn.kkwli.travel.dao.FavoriteDao;
import cn.kkwli.travel.dao.impl.FavoriteDaoImpl;

import cn.kkwli.travel.service.FavouriteService;



public class FavouriteServiceImpl implements FavouriteService {
    private final FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean findOne(int rid, int uid) {
        return favoriteDao.findExist(rid, uid) == 1;
    }

    @Override
    public boolean addOne(int rid, int uid) {
        if (rid != 0 && uid != 0) {
            return favoriteDao.addOne(rid, uid);
        } else
            return false;
    }
    @Override
    public boolean removeOne(int rid, int uid) {
        if (rid != 0 && uid != 0) {
            return favoriteDao.removeOne(rid, uid);
        } else
            return false;
    }


}
