package mapper;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.User;

import java.util.List;
import java.util.Map;

/**
 * Created by zhans-pc on 2017/5/4.
 */
@Repository
public interface UserMapper {
    // 查找
    public List<User> selectAll();

    // 添加
    public void addUser(User user);

    public Map<String ,User> selectOne(String id);
}
