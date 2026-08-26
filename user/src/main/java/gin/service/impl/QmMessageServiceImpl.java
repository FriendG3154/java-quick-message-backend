package gin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gin.entity.QmMessage;
import gin.service.QmMessageService;
import gin.mapper.QmMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author gin
* @description 针对表【qm_message】的数据库操作Service实现
* @createDate 2026-08-25 17:29:40
*/
@Service
public class QmMessageServiceImpl extends ServiceImpl<QmMessageMapper, QmMessage>
    implements QmMessageService{

}




