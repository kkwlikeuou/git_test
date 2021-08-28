package cn.kkwli.travel.web.servlet;

import cn.kkwli.travel.service.FavouriteService;
import cn.kkwli.travel.service.RouteService;
import cn.kkwli.travel.service.impl.FavouriteServiceImpl;
import cn.kkwli.travel.service.impl.RouteServiceImpl;
import cn.kkwli.travel.util.JSONUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/favourite/*")
public class FavouriteServlet extends BaseServlet {
    private final FavouriteService favouriteService = new FavouriteServiceImpl();

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        String uidStr = request.getParameter("uid");
        int rid = toInt(ridStr);
        int uid = toInt(uidStr);
        boolean favorite = favouriteService.findOne(rid, uid);

        new JSONUtil().writeValue(favorite, response);

    }

    public void addOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        String uidStr = request.getParameter("uid");
        int rid = toInt(ridStr);
        int uid = toInt(uidStr);
        boolean flag = favouriteService.addOne(rid, uid);
        RouteService routeService = new RouteServiceImpl();
        routeService.plusCount(rid);
        new JSONUtil().writeValue(flag, response);

    }

    public void removeOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        String uidStr = request.getParameter("uid");
        int rid = toInt(ridStr);
        int uid = toInt(uidStr);
        boolean flag = favouriteService.removeOne(rid, uid);
        RouteService routeService = new RouteServiceImpl();
        routeService.subCount(rid);
        new JSONUtil().writeValue(flag, response);

    }

    private int toInt(String str) {
        int num = 0;
        if (str != null && str.length() > 0 && !"null".equals(str)) {
            num = Integer.parseInt(str);
        }
        return num;
    }




}
