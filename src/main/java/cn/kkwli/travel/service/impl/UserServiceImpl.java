package cn.kkwli.travel.service.impl;

import cn.kkwli.travel.dao.UserDao;
import cn.kkwli.travel.dao.impl.UserDaoImpl;
import cn.kkwli.travel.domain.User;
import cn.kkwli.travel.service.UserService;
import cn.kkwli.travel.util.MailUtils;
import cn.kkwli.travel.util.UuidUtil;


public class UserServiceImpl implements UserService {
    private final UserDao dao = new UserDaoImpl();

    public Boolean register(User user) {

        User u = dao.findByUsername(user.getUsername());
        if (u != null) {
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        dao.save(user);
        String content = "<a href='http://117.63.228.101:8000/user/active?code=" + user.getCode() + "'>点击激活</a>";
        MailUtils.sendMail(user.getEmail(), content, "这是一封激活邮件，请勿泄露");
        return true;

    }

    @Override
    public Boolean active(String code) {
        User user = dao.findByCode(code);
        if (user != null) {
            dao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        return dao.findByUsername(user.getUsername(), user.getPassword());
    }

}
