package cn.kkwli.travel.web.servlet;

import cn.kkwli.travel.domain.PageBean;
import cn.kkwli.travel.domain.Route;
import cn.kkwli.travel.service.RouteService;
import cn.kkwli.travel.service.impl.RouteServiceImpl;
import cn.kkwli.travel.util.JSONUtil;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private final RouteService service = new RouteServiceImpl();
    private final JSONUtil jsonUtil = new JSONUtil();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        String uidStr = request.getParameter("uid");
        int pageSize = 5;
        int uid = 0;
        if (uidStr != null && uidStr.length() > 0 && !"null".equals(uidStr)) {
            uid = Integer.parseInt(uidStr);
            pageSize = 16;
        }

        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
            pageSize = 5;
        }

        int currentPage = 1;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        }


        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        PageBean<Route> pb = service.pageQuery(cid, uid, currentPage, pageSize, rname);
        jsonUtil.writeValue(pb, response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        if (ridStr != null && ridStr.length() > 0 && !"null".equals(ridStr)) {
            int rid = Integer.parseInt(ridStr);
            Route route = service.findByRid(rid);
            jsonUtil.writeValue(route, response);
        }
    }

    public void findCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        if (ridStr != null && ridStr.length() > 0 && !"null".equals(ridStr)) {
            int rid = Integer.parseInt(ridStr);
            int count = service.findCount(rid);
            jsonUtil.writeValue(count, response);
        }
    }

}
