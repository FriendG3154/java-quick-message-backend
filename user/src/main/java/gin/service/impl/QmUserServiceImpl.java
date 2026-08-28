package gin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gin.config.JwtConfig;
import gin.entity.QmUser;
import gin.entity.QmUserAuth;
import gin.mapper.QmUserAuthMapper;
import gin.model.QmUser.QmUserAuthVO;
import gin.model.QmUser.QmUserInput;
import gin.model.QmUser.UserLoginInfo;
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
    private final JwtConfig jwtConfig;

    public QmUserServiceImpl(QmUserAuthMapper qmUserAuthMapper, RedisService redis, JwtTool jwtTool, JwtConfig jwtConfig) {
        this.qmUserAuthMapper = qmUserAuthMapper;
        this.redis = redis;
        this.jwtTool = jwtTool;
        this.jwtConfig = jwtConfig;
    }

    /**
     * 登录后获取token
     */
    @Override
    public UserLoginInfo login(QmUserInput qmUserInput) throws Exception {
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
        QmUserAuthVO result = baseMapper.selectUserWithAuth(qmUserInput.getPhone(),null);
        String token = jwtTool.generateToken(result);
        String refreshToken = jwtTool.generateReFreshToken(result);
        Long expirer = jwtConfig.getExpire();
        Long refreshExpire = jwtConfig.getRefreshExpire();
        redis.setRawString("login:token:" + result.getUserId(),token, expirer/1000 );
        redis.setRawString("login:refreshToken:" + result.getUserId(),refreshToken, refreshExpire/1000 );
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setToken(token);
        userLoginInfo.setRefreshToken(refreshToken);
        userLoginInfo.setExpire(expirer);
        userLoginInfo.setRefreshExpire(refreshExpire);
        return userLoginInfo;
    }

    /**
     * 刷新token
     */
    @Override
    public UserLoginInfo refreshToken(String token){
        QmUserAuthVO authVo =  jwtTool.validateToken(token);
        String userId = authVo.getUserId();//如果用户在数据库内，刷新token，以及对应携带的身份和全县信息;
        QmUserAuthVO result = baseMapper.selectUserWithAuth(null,userId);
        if(result != null){
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            String newToken = jwtTool.generateToken(result);
            userLoginInfo.setToken(newToken);
            String newRefreshToken = jwtTool.generateReFreshToken(result);
            redis.setRawString("login:token:" + userId, newToken, jwtConfig.getExpire() / 1000);
            redis.setRawString("login:refreshToken:" + userId, newRefreshToken, jwtConfig.getRefreshExpire() / 1000);
            userLoginInfo.setRefreshToken(newRefreshToken);
            userLoginInfo.setExpire(jwtConfig.getExpire()); // Set expire
            userLoginInfo.setRefreshExpire(jwtConfig.getRefreshExpire()); // Set refresh expire
            return userLoginInfo;
        }
        return null;
    }
    
}




