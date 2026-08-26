package gin.service;

import gin.entity.QmUser;
import com.baomidou.mybatisplus.extension.service.IService;
import gin.model.QmUser.QmUserAuthVO;
import gin.model.QmUser.QmUserInput;

/**
* @author gin
* @description 针对表【qm_user】的数据库操作Service
* @createDate 2026-08-25 17:29:40
*/
public interface QmUserService extends IService<QmUser> {

    public String login(QmUserInput qmUserInput) throws Exception;
}
