package com.bluexiii.jwh.util;

import com.bluexiii.jwh.exception.ResourceNotFoundException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
 * Created by bluexiii on 16/9/8.
 */
public class RestUtil {

    /**
     * 校验是否为空
     * @param resource
     * @param <T>
     * @return
     */
    public static <T> T checkResourceFound(final T resource) {
        if (resource == null) {
            throw new ResourceNotFoundException("找不到资源");
        }
        return resource;
    }

    /**
     * 新增资源后在header中添加url
     * @param response
     * @param id
     */
    public static void setHeaderLocation(HttpServletResponse response, Object id) {
        String location = ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}").buildAndExpand(Collections.singletonMap("id", id)).toUriString();
        response.setHeader("Location", location);
    }

}
