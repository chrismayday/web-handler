package com.bluexiii.jwh.handler;

import com.bluexiii.jwh.component.RequestLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bluexiii on 16/9/20.
 */
@Component
public class ControllerLoggingHandler extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLoggingHandler.class);

    @Autowired(required = false)
    private RequestLogService requestLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String requestURI = request.getRequestURI();
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long processTime = endTime - startTime;
        String userName;
        try {
            userName = request.getUserPrincipal().getName();
        } catch (NullPointerException e) {
            userName = "unknown";
        }
        String remoteAddr = request.getRemoteAddr();
        LOGGER.debug("RequestLog  requestURI:{}  processTime:{}ms  userName:{} remoteAddr:{}", requestURI, processTime, userName, remoteAddr);
        if (requestLogService != null) {
            requestLogService.saveLog(requestURI, processTime, userName, remoteAddr);
        }
    }

}
