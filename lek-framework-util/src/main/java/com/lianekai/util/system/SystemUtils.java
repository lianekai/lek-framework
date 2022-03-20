package com.lianekai.util.system;

import com.lianekai.util.collection.CollectionUtils;
import com.lianekai.util.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 系统工具类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/20 14:14
 */
@Slf4j
public class SystemUtils {

    private SystemUtils(){}

    /**
     * 获取环境变量值
     * 如果为空，则返回系统属性值
     * 也为空则返回defaultValue
     *
     * @param envName 环境变量或系统属性名
     * @param defaultValue 默认值
     * @return String 获取的变量值
     */
    public static String getEnv(String envName,String defaultValue)
    {
        String envValue;
        envValue=System.getenv(envName);
        if(!StringUtils.isBlank(envValue))
        {
            return envValue;
        }
        envValue=System.getProperty(
                envName);
        return StringUtils.isBlank(envValue)?defaultValue:envValue;
    }

    /**
     * 获取环境变量值
     * 如果为空，则返回系统属性值
     * 也为空则返回null
     *
     * @param envName 环境变量或系统属性名
     * @return String 获取的变量值
     */
    public static String getEnv(String envName)
    {
        return getEnv(envName,null);
    }

    /**
     * 获取排序后第一个Mac地址
     *
     * @return String 第一个Mac值
     */
    public static String getFirstMacAddress() {
        List<String> macList = getMacAddressList();
        if (CollectionUtils.isEmpty(macList)) {
            return null;
        }

        return macList.get(0);
    }


    /**
     * 获取Mac地址列表（已排序）
     *
     * @return List<String> Mac地址列表
     */
    public static List<String> getMacAddressList() {
        InetAddress ip;
        NetworkInterface netInterface;
        List<String> macList = new ArrayList<>();

        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                netInterface = netInterfaces.nextElement();

                // 获取IP地址列表
                Enumeration<InetAddress> ips = netInterface.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
                        macList.add(getMacFromBytes(netInterface.getHardwareAddress()));
                    }
                }
            }
        } catch (Exception e) {
            log.error("Get the MAC address exception", e);
        }

        // 排序
        Collections.sort(macList);
        return macList;
    }

    /**
     * 获取服务名
     *
     * @return String 服务名
     */
    public static String getServerName() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostName();
        } catch (UnknownHostException e) {
            log.error("Get server name exception", e);
        }
        return null;
    }

    /**
     * 字节转Mac地址
     *
     * @param bytes 地址数组
     * @return String Mac地址
     */
    private static String getMacFromBytes(byte[] bytes) {
        StringBuilder mac = new StringBuilder();
        byte currentByte;
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append(Constant.DASH);
            }
            currentByte = (byte)((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte)(b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }
}
