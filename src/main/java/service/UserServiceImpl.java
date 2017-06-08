package service;

import mapper.UserMapper;
import org.springframework.stereotype.Service;
import pojo.User;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zhans-pc on 2017/5/4.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    public List<User> findUser() throws Exception {
        return userMapper.selectAll();
    }

    public void addUser(User user) {
        userMapper.addUser(user);
    }

    public Map<String, User> findById(String id) {
        return userMapper.selectOne(id);
    }
}
