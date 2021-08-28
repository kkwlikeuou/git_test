package cn.kkwli.travel.web.servlet;

import cn.kkwli.travel.domain.ResultInfo;
import cn.kkwli.travel.domain.User;
import cn.kkwli.travel.service.UserService;
import cn.kkwli.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private final UserService service = new UserServiceImpl();

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checked_server = (String) session.getAttribute("CHECKED_SERVER");
        session.removeAttribute("CHECKED_SERVER");
        ResultInfo info = new ResultInfo();
        if (checked_server == null || !checked_server.equalsIgnoreCase(check)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Boolean flag = service.register(user);

        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");

        HttpSession session = request.getSession();
        String checked_server = (String) session.getAttribute("CHECKED_SERVER");

        ResultInfo info = new ResultInfo();
        if (checked_server == null || !checked_server.equalsIgnoreCase(check)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        } else {
            //1.获取用户名和密码数据
            Map<String, String[]> map = request.getParameterMap();
            //2.封装User对象
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //3.调用Service查询
            User u = service.login(user);

            //4.判断用户对象是否为null
            if (u == null) {
                //用户名密码或错误
                info.setFlag(false);
                info.setErrorMsg("用户名密码或错误");
            }
            //5.判断用户是否激活
            if (u != null && !"Y".equals(u.getStatus())) {
                //用户尚未激活
                info.setFlag(false);
                info.setErrorMsg("您尚未激活，请激活");
            }
            //6.判断登录成功
            if (u != null && "Y".equals(u.getStatus())) {
                String flag = request.getParameter("flag");
                request.getSession().setAttribute("user", u);//登录成功标记
                /*if ("off".equals(flag)) {
                    Cookie cookie = new Cookie("username", u.getUsername() + "-" + u.getPassword());
                    cookie.setMaxAge();
                }*/
                //登录成功
                info.setFlag(true);
            }
        }

        //响应数据
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null) {
            Boolean flag = service.active(code);
            String msg;
            if (flag) {
                msg = "激活成功,请<a href='../login.html'>登录<a>";
            } else {
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

}
