package com.ooteco.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Service
public class IpAddressService {
    private static Logger log = LoggerFactory.getLogger(IpAddressService.class);

    @Value("${geolite2.city-db.path}")
    private String dbPath;

    private static DatabaseReader reader;

    @PostConstruct
    public void init() {
        try {
            File database = new File(dbPath);
            reader = new DatabaseReader.Builder(database).build();
        } catch (Exception e) {
            log.error("IP地址服务初始化异常:" + e.getMessage(), e);
        }
    }

    public Map<String, String> getAddress(String ipAddress){
        try {
            CityResponse response = reader.city(InetAddress.getByName(ipAddress));
            Map<String, String> dataMap = new HashMap<>();
            Country country = response.getCountry();
            dataMap.put("country", country.getNames().get("en"));
            Subdivision subdivision = response.getMostSpecificSubdivision();
            dataMap.put("subdivision", subdivision.getNames().get("en"));
            City city = response.getCity();
            dataMap.put("city", city.getNames().get("en"));

            return dataMap;
        }catch (Exception e){
            log.error("根据IP[{}]获取地址失败:{}", ipAddress, e.getMessage());
            return null;
        }
    }

    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static void main(String[] args) throws IOException, GeoIp2Exception {
        File database = new File("D://GeoLite2/GeoLite2-City.mmdb");
        reader = new DatabaseReader.Builder(database).build();
        DatabaseReader reader1 = new DatabaseReader.Builder(database).build();

        CityResponse response = reader.city(InetAddress.getByName("119.137.52.33"));
        Country country = response.getCountry();
        System.out.println(country.getNames().get("en"));


    }

}
