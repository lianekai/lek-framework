package com.lianekai.util.http;

import com.lianekai.util.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Optional;

/**
 * HTTP工具类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/18 22:16
 */
@Slf4j
public class HttpUtils {

    private HttpUtils(){}

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";


    public static final String JSON_REQUEST_PREFIX = "application/json;";

    private static final String UNKNOWN = "unknown";
    /**
     * 本地IP
     */
    private static final String LOCAL_IP = "127.0.0.1";
    /**
     * 本地IP
     */
    private static final String LOCAL_IP_2 = "0:0:0:0:0:0:0:1";


    /**
     *  判断请求类型是否为json类型
     * @param contentType contentType
     * @return
     */
    public static boolean isJsonContentType(String contentType) {
        return StringUtils.isNotBlank(contentType) && contentType.contains(JSON_REQUEST_PREFIX);
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     *
     * @param request 请求
     * @return String ip地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ipIsBank(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ipIsBank(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipIsBank(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipIsBank(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ipIsBank(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ipIsBank(ip)) {
            ip = request.getRemoteAddr();
            // 根据网卡取本机配置的IP
            if (LOCAL_IP.equals( ip) || LOCAL_IP_2.equals(ip)) {
                try {
                    InetAddress inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("获取IP地址失败", e);
                }
            }
        }

        if (StringUtils.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ip.contains(Constant.COMMA_EN)) {
                ip = ip.split(Constant.COMMA_EN)[0];
            }
        }
        return ip;
    }

    /**
     * 获取请求cookie值
     *
     * @param request 请求
     * @param cookieName cookie名
     * @return String cookie值
     */
    public static String getCookieValue(HttpServletRequest request,String cookieName)
    {
        Cookie[] cookies = request.getCookies();
        if(cookies==null || cookies.length==0)
        {
            return null;
        }
        Optional<Cookie> targetTokenCookieOptional= Arrays.stream(cookies).filter(t -> cookieName.equals(t.getName())).findFirst();

        return targetTokenCookieOptional.map(Cookie::getValue).orElse(null);
    }

    /**
     * 获取请求请求头值
     *
     * @param request 请求
     * @param headerName 请求头名
     * @return String 请求头值
     */
    public static String getHeaderValue(HttpServletRequest request,String headerName)
    {
        return request.getHeader(headerName);
    }

    /**
     * 获取请求参数值
     *
     * @param request 请求
     * @param paramName 参数名
     * @return String 参数值
     */
    public static String getRequestParam(HttpServletRequest request,String paramName)
    {
        return request.getParameter(paramName);
    }

    /**
     * 校验IP是否为空
     *
     * @param ip ip字符串
     * @return boolean ip地址是否为空
     */
    private static boolean ipIsBank(String ip) {
        return StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip);
    }

}
