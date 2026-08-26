package gin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gin.entity.QmUser;
import gin.mapper.QmUserAuthMapper;
import gin.model.QmUser.QmUserAuthVO;
import gin.model.QmUser.QmUserInput;
import gin.service.QmUserService;
import gin.mapper.QmUserMapper;
import gin.service.util.RedisService;
import gin.tool.JwtTool;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
* @author gin
* @description 针对表【qm_user】的数据库操作Service实现
* @createDate 2026-08-25 17:29:40
*/
@Service
public class QmUserServiceImpl extends ServiceImpl<QmUserMapper, QmUser>
    implements QmUserService{
    private final QmUserAuthMapper qmUserAuthMapper;
    private final RedisService redis;
    private final JwtTool jwtTool;
    public QmUserServiceImpl(QmUserAuthMapper qmUserAuthMapper, RedisService redis,JwtTool jwtTool) {
        this.qmUserAuthMapper = qmUserAuthMapper;
        this.redis = redis;
        this.jwtTool = jwtTool;
    }

    /**
     * 登录后获取token
     * @param qmUserInput
     * @return
     * @throws Exception
     */
    @Override
    public String login(QmUserInput qmUserInput) throws Exception {
        var isExist = lambdaQuery().eq(QmUser::getPhone, qmUserInput.getPhone()).exists();//判断是否存在手机号；
        if(!isExist){
            throw new Exception("手机号不存在");
        }
        var localStorageCode = redis.getString(qmUserInput.getPhone()); // 获取本地存储的验证码
        if (localStorageCode == null) {
            throw new Exception("验证码已过期");
        }
        if( !localStorageCode.equals(qmUserInput.getCode())){
            throw new Exception("验证码错误");
        }
        redis.deleteString(qmUserInput.getPhone());
        QmUserAuthVO result = baseMapper.selectUserWithAuth(qmUserInput.getPhone());
        return jwtTool.generateToken(result);
    }
}




