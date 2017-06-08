package service;

import pojo.User;

import java.util.List;
import java.util.Map;

/**
 * Created by zhans-pc on 2017/5/4.
 */

public interface UserService {
    List<User> findUser() throws Exception;
    void addUser(User user);
    Map<String,User> findById(String id);
}
