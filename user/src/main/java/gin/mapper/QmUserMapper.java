package gin.mapper;

import gin.entity.QmUser;
import gin.model.QmUser.QmUserAuthVO;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author gin
* @description 针对表【qm_user】的数据库操作Mapper
* @createDate 2026-08-25 17:29:40
* @Entity gin.entity.QmUser
*/
public interface QmUserMapper extends BaseMapper<QmUser> {
    QmUserAuthVO selectUserWithAuth(@Param("phone") String phone, @Param("userId") String userId);
}




