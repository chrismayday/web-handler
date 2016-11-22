package com.bluexiii.jwh.component;

import com.bluexiii.jwh.domain.RequestLog;
import com.bluexiii.jwh.repository.RequestLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Mockito示例
 * Created by bluexiii on 16/9/20.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Transactional
public class RequestLogServiceTest {
    @Mock
    private RequestLogRepository requestLogRepository;
    private RequestLogService requestLogService;

    @Before
    public void setUp() throws Exception {
        requestLogService = new RequestLogService(requestLogRepository);
        stubRepository();
    }

    private void stubRepository() {
        RequestLog requestLog = new RequestLog("/logtest", 8L, "adimin", "127.0.0.1");
        when(requestLogRepository.findOne(anyLong())).thenReturn(requestLog);
    }

    @Test
    public void saveLog() throws Exception {
        requestLogService.saveLog("/rest/logtest", 1L, "adimin", "127.0.0.1");
        verify(requestLogRepository, times(1)).save(any(RequestLog.class));
    }

    @Test
    public void findLog() throws Exception {
        RequestLog requestLog = requestLogService.findLog(21L);
        System.out.println(requestLog);
        verify(requestLogRepository, times(1)).findOne(any(Long.class));
    }
}