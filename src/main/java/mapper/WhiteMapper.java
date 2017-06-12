package mapper;

import org.springframework.stereotype.Repository;
import pojo.MtaHotelWhiteList;

import java.util.List;

/**
 * Created by zhans-pc on 2017/6/11.
 */
@Repository
public interface WhiteMapper {
    public int batchInsert(List<MtaHotelWhiteList> lists);
}
