package com.bluexiii.jwh.component;

import com.bluexiii.jwh.domain.RequestLog;
import com.bluexiii.jwh.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bluexiii on 16/9/20.
 */
@Component
@Transactional
public class RequestLogService {
    private final RequestLogRepository requestLogRepository;

    @Autowired
    public RequestLogService(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    public void saveLog(String requestUri, Long processTime, String userName, String remoteAddr) {
        RequestLog requestLog = new RequestLog(requestUri, processTime, userName, remoteAddr);
        requestLogRepository.save(requestLog);
    }

    public RequestLog findLog(long id) {
        return requestLogRepository.findOne(id);
    }
}
