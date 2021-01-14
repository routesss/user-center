package com.sola.usercenter.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * sentinel请求来源处理  SentinelResource不支持来源
 */
@Slf4j
@Component
public class MyRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        //从参数中获取origin并返回
        String origin = httpServletRequest.getParameter("origin");
        if(StringUtils.isBlank(origin)){
            origin = "unknown";
        }
        log.info("sentinel origin : {}", origin);
        return origin;
    }
}
