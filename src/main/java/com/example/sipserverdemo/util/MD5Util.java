package com.example.sipserverdemo.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: cth
 * @Date: 2019/5/16 17:42
 * @Description:
 */
public class MD5Util {
    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String MD5(String text) {
        //加密后的字符串
        String encodeStr = DigestUtils.md5Hex(text);
        return encodeStr;
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key  密钥
     * @param md5  密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = MD5(text + key);
        if (md5Text.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        String a = "<?xml version=\"1.0\" encoding=\"gb2312\"?>\n" +
                "<Response>\n" +
                "<CmdType>Catalog</CmdType>\n" +
                "<SN>20000</SN>\n" +
                "<DeviceID>34020000001110000001</DeviceID>\n" +
                "<SumNum>10</SumNum>\n" +
                "<DeviceList Num=\"2\">\n" +
                "<Item>\n" +
                "<DeviceID>34020000001320000009</DeviceID>\n" +
                "<Name>Camera 01</Name>\n" +
                "<Manufacturer>Manufacturer</Manufacturer>\n" +
                "<Model>Camera</Model>\n" +
                "<Owner>Owner</Owner>\n" +
                "<CivilCode>CivilCode</CivilCode>\n" +
                "<Address>172.18.1.129</Address>\n" +
                "<Parental>0</Parental>\n" +
                "<SafetyWay>0</SafetyWay>\n" +
                "<RegisterWay>1</RegisterWay>\n" +
                "<Secrecy>0</Secrecy>\n" +
                "<Status>ON</Status>\n" +
                "</Item>\n" +
                "<Item>\n" +
                "<DeviceID>34020000001320000010</DeviceID>\n" +
                "<Name>Camera 01</Name>\n" +
                "<Manufacturer>Manufacturer</Manufacturer>\n" +
                "<Model>Camera</Model>\n" +
                "<Owner>Owner</Owner>\n" +
                "<CivilCode>CivilCode</CivilCode>\n" +
                "<Address>172.18.1.130</Address>\n" +
                "<Parental>0</Parental>\n" +
                "<SafetyWay>0</SafetyWay>\n" +
                "<RegisterWay>1</RegisterWay>\n" +
                "<Secrecy>0</Secrecy>\n" +
                "<Status>ON</Status>\n" +
                "</Item>\n" +
                "</DeviceList>\n" +
                "</Response>";
        String b = StringUtils.substringAfter(a, "<Item>");
        String c = StringUtils.substringBefore(b, "</DeviceList>");
        System.out.println("34020000001320000010".substring(10,13));


    }
}