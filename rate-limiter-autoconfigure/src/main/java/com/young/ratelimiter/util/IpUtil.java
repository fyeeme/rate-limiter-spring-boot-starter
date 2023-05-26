package com.young.ratelimiter.util;


import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class IpUtil {
    private static final String UNKNOWN = "unknown";
    private static final List<String> LOCAL_HOSTS = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");
    private static final String SEPARATOR = ",";

    private IpUtil() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCAL_HOSTS.contains(ipAddress)) {
                    ipAddress = getLocalHost(ipAddress);
                }
            }

            if (ipAddress != null && ipAddress.length() > 15 && ipAddress.contains(SEPARATOR)) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    public static Boolean checkIpMatched(List<String> ipWhiteList, String ip) {
        for (String ipReg : ipWhiteList) {
            if (ipReg.equals(ip)) {
                return true;
            }
            if (ipReg.contains("*") && Boolean.TRUE.equals(matchIp(ipReg, ip))) {
                return true;
            }
        }

        return false;
    }

    private static Boolean matchIp(String reg, String ip) {
        String[] regChildren = reg.split("\\.");
        String[] ipChildren = ip.split("\\.");
        boolean matched = true;
        int index = 0;
        for (String regChild : regChildren) {
            matched = "*".equals(regChild) || regChild.equals(ipChildren[index]);
            if (!matched) {
                break;
            }
            index++;
        }
        return matched;
    }

    private static String getLocalHost(String ipAddress) {
        InetAddress inet = null;
        try {
            inet = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (inet != null) {
            ipAddress = inet.getHostAddress();
        }
        return ipAddress;
    }
}
