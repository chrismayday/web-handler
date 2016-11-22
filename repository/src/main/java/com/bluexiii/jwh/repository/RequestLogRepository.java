package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;


/**
 * Created by bluexiii on 16/8/25.
 */
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
    Long countByCreatedAtBetween(Date start, Date end);
}
