package cn.kkwli.travel.dao;

import cn.kkwli.travel.domain.Seller;

public interface SellDao {
    Seller findBySid(int sid);
}
