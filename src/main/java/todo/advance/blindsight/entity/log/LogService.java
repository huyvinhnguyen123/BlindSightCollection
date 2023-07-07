package todo.advance.blindsight.entity.log;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public void saveDebugLog(String messsage) {
        Log log = new Log();
        log.setMessage(messsage);
        log.setLevel(LogLevelEnum.DEBUG.toString());
        logRepository.save(log);
    }

    public void saveInfoLog(String messsage) {
        Log log = new Log();
        log.setMessage(messsage);
        log.setLevel(LogLevelEnum.INFO.toString());
        logRepository.save(log);
    }

    public void saveWarnLog(String messsage) {
        Log log = new Log();
        log.setMessage(messsage);
        log.setLevel(LogLevelEnum.WARN.toString());
        logRepository.save(log);
    }

    public void saveErrorLog(String messsage) {
        Log log = new Log();
        log.setMessage(messsage);
        log.setLevel(LogLevelEnum.ERROR.toString());
        logRepository.save(log);
    }

    public void saveTraceLog(String messsage) {
        Log log = new Log();
        log.setMessage(messsage);
        log.setLevel(LogLevelEnum.TRACE.toString());
        logRepository.save(log);
    }
}
