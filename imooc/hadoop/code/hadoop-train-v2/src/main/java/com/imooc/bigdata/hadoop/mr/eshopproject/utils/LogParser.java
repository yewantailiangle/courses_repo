package com.imooc.bigdata.hadoop.mr.eshopproject.utils;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class LogParser {
    public Map<String, String> parse(String log) {
        Map<String, String> info = new HashMap<>();
        IPParser ipParser = IPParser.getInstance();

        if (StringUtils.isNotBlank(log)) {
            //"^A"是"\0001"
            String[] splits = log.split("\u0001");
            String ip = splits[13];

            String country = "-";
            String province = "-";
            String city = "-";
            IPParser.RegionInfo regionInfo = ipParser.analyseIp(ip);

            if (regionInfo != null) {
                country = regionInfo.getCountry();
                province = regionInfo.getProvince();
                city = regionInfo.getCity();
            }

            info.put("ip", ip);
            info.put("country", country);
            info.put("province", province);
            info.put("city", city);

            String url = splits[1];
            info.put("url", url);

            String time = splits[17];
            info.put("time", time);
        }

        return info;
    }

    public Map<String, String> parseV2(String log) {
        Map<String, String> info = new HashMap<>();
        IPParser ipParser = IPParser.getInstance();

        if (StringUtils.isNotBlank(log)) {
            //V2 ETL版本是通过\t分隔
            String[] splits = log.split("\t");

            String ip = splits[0];
            String country = splits[1];
            String province = splits[2];
            String city = splits[3];

            info.put("ip", ip);
            info.put("country", country);
            info.put("province", province);
            info.put("city", city);

            String url = splits[4];
            info.put("url", url);

            String time = splits[5];
            info.put("time", time);

            String pageId = splits[6];
            info.put("pageId", pageId);
        }

        return info;
    }
}
