package com.example.sipserverdemo.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description:SIP信令中的SSRC工具类。SSRC值由10位十进制整数组成的字符串，第一位为0代表实况，为1则代表回放；
 * 第二位至第六位由监控域ID的第4位到第8位组成；最后4位为不重复的4个整数
 * @author: skyAndMoon
 * @date: 2020年5月10日 上午11:57:57
 */
@Component
public class SsrcUtil {

	private static String ssrcPrefix;

	private static List<String> isUsed;

	private static List<String> notUsed;

	private static String GB_RTMP_FORMAT = "rtmp://%s:%d/%s";

	private static String GB_FLV_FORMAT = "http://%s:%d/%s.flv";

	private static String GB_WEBRTC_FORMAT = "webrtc://%s/%s";

	private static void init() {
		isUsed = new ArrayList<String>();
		notUsed = new ArrayList<String>();
		for (int i = 1; i < 10000; i++) {
			if (i < 10) {
				notUsed.add("000" + i);
			} else if (i < 100) {
				notUsed.add("00" + i);
			} else if (i < 1000) {
				notUsed.add("0" + i);
			} else {
				notUsed.add(String.valueOf(i));
			}
		}
	}

	/**
	 * 获取视频预览的SSRC值,第一位固定为0
	 *
	 */
	public static String getPlaySsrc(Long cameraId) {
		return getSsrcPrefix() + cameraId;
	}

	/**
	 * 获取视频预览的SSRC值,第一位固定为0
	 *
	 */
	public static String getPlaySsrc() {
		return getSsrcPrefix();
	}

	/**
	 * 释放ssrc，主要用完的ssrc一定要释放，否则会耗尽
	 *
	 */
	public static void releaseSsrc(String ssrc) {
		String sn = ssrc.substring(6);
		isUsed.remove(sn);
		notUsed.add(sn);
	}

	public static String getRandomString(int length){
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	/**
	 * 获取后四位数SN,随机数
	 */
	private static String getSN() {
		String sn = null;
		if (notUsed.size() == 0) {
			throw new RuntimeException("ssrc已经用完");
		} else if (notUsed.size() == 1) {
			sn = notUsed.get(0);
		} else {
			sn = notUsed.get(new Random().nextInt(notUsed.size() - 1));
		}
		notUsed.remove(0);
		isUsed.add(sn);
		return sn;
	}

	private static String getSsrcPrefix() {
		if (ssrcPrefix == null) {
			init();
		}
		return ssrcPrefix;
	}

	public static String getPlayStreamID(String mediaType, String ssrc) {
		String streamID;
		switch (mediaType.toUpperCase()){
			case "SRS":
				streamID = "live/chid" + ssrc;
				break;
			case "ZLM":
				streamID = "rtp/" + Long.toHexString(Long.parseLong(ssrc));
				break;
			default:
				streamID = "";
				break;
		}
		return streamID;
	}

	public static String getRtmp(String mediaType, String ip, Integer port, String ssrc) {
		String streamID = getPlayStreamID(mediaType,ssrc);
		return String.format(GB_RTMP_FORMAT,ip,port, streamID);
	}

	public static String getFlv(String mediaType,String ip, Integer port, String ssrc) {
		String streamID = getPlayStreamID(mediaType,ssrc);
		return String.format(GB_FLV_FORMAT,ip,port, streamID);
	}

	public static String getWebRtc(String mediaType,String ip, String ssrc) {
		String streamID = getPlayStreamID(mediaType,ssrc);
		return String.format(GB_WEBRTC_FORMAT,ip,streamID);
	}
}
