package top.liubaiblog.admin.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.liubaiblog.admin.mapper.OperLogMapper;
import top.liubaiblog.admin.pojo.OperLog;

/**
 * @author 刘佳俊
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private OperLogMapper operLogMapper;


    @Override
    @Async
    public void addLog(OperLog operLog) {
        operLogMapper.insert(operLog);
    }
}
