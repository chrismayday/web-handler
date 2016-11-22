package com.bluexiii.jwh.service;

import com.bluexiii.jwh.dto.LoginChartDTO;
import com.bluexiii.jwh.dto.SummaryDTO;
import com.bluexiii.jwh.repository.CategRepository;
import com.bluexiii.jwh.repository.GdsRepository;
import com.bluexiii.jwh.repository.RequestLogRepository;
import com.bluexiii.jwh.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by bluexiii on 2016/9/29.
 */
@Service
@Transactional
public class StatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final CategRepository categRepository;
    private final GdsRepository gdsRepository;
    private final UserRepository userRepository;
    private final RequestLogRepository requestLogRepository;
    private final JdbcTemplate jdbcTemplate;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public StatService(GdsRepository gdsRepository, CategRepository categRepository, UserRepository userRepository, RequestLogRepository requestLogRepository, JdbcTemplate jdbcTemplate) {
        this.gdsRepository = gdsRepository;
        this.categRepository = categRepository;
        this.userRepository = userRepository;
        this.requestLogRepository = requestLogRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 首页汇总图表
     *
     * @return
     */
    public SummaryDTO summary() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        Date start = Date.from(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LOGGER.debug(start.toString());
        LOGGER.debug(end.toString());

        long categCount = categRepository.count();
        long gdsCount = gdsRepository.count();
        long userCount = userRepository.count();
        long requsetCount = requestLogRepository.countByCreatedAtBetween(start,end);

        SummaryDTO summaryDTO = new SummaryDTO();
        summaryDTO.setCategCount(categCount);
        summaryDTO.setGdsCount(gdsCount);
        summaryDTO.setUserCount(userCount);
        summaryDTO.setRequestCount(requsetCount);
        return summaryDTO;
    }

    /**
     * 首页登录次数图表
     *
     * @return
     */
    public LoginChartDTO apiChart(String apiName, Integer days) {
        String sql = "select DATE(created_at) as statDate ,count(*) as loginCount,count(distinct remote_addr) as ipCount from request_log where request_uri='"
                + apiName + "' and created_at > date_sub(curdate(), INTERVAL " + days + " DAY) group by DATE(created_at)";
        LOGGER.debug(sql);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        List<String> statDateList = new LinkedList<>();
        List<Long> loginCountList = new LinkedList<>();
        List<Long> ipCountList = new LinkedList<>();
        for (Map<String, Object> map : maps) {
            Date statDate = (Date) map.get("statDate");
            Long loginCount = (Long) map.get("loginCount");
            Long ipCount = (Long) map.get("ipCount");
            statDateList.add(df.format(statDate));
            loginCountList.add(loginCount);
            ipCountList.add(ipCount);
        }

        LoginChartDTO loginChartDTO = new LoginChartDTO();
        loginChartDTO.setStatDate(statDateList);
        loginChartDTO.setLoginCount(loginCountList);
        loginChartDTO.setIpCount(ipCountList);
        return loginChartDTO;
    }


}
