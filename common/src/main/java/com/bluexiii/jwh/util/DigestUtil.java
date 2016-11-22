package com.bluexiii.jwh.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @Filename DigestUtil.java
 * @Description 签名工具类
 * @Version 1.0
 */
public class DigestUtil {

    private static final Logger logger = LoggerFactory.getLogger(DigestUtil.class.getName());

    /**
     * 签名编码
     */
    public static final String UTF8 = "utf-8";
    /**
     * 签名key
     */
    public static final String SIGN_KEY = "sign";

    /**
     * 渠道id key
     */
    public static final String CHANNEL_ID_KEY = "channelId";

    /**
     * 签名类型key，支持DigestALGEnum
     */
    public static final String SIGN_TYPE_KEY = "signType";

    /**
     * utc时间key
     */
    public static final String TIMESTAMP_KEY = "utc_time_stamp";

    /**
     * 签名算法
     *
     * @Filename DigestUtil.java
     * @Description
     * @Version 1.0
     * @Author bohr.qiu
     * @Email qzhanbo@yiji.com
     * @History <li>Author: bohr.qiu</li> <li>Date: 2013-1-5</li> <li>Version:
     * 1.0</li> <li>Content: create</li>
     */
    public static enum DigestALGEnum {
        SHA256("SHA-256"), MD5("MD5");
        private String name;

        DigestALGEnum(String name) {
            this.name = name;
        }

        public static DigestALGEnum getByName(String name) {
            for (DigestALGEnum _enum : values()) {
                if (_enum.getName().equals(name)) {
                    return _enum;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 以Map中key的字符顺序排序后签名，如果secretKey不为空，排在最后面签名。<br/>
     * 比如：Map中值如下：<br/>
     * keyA=valueA<br/>
     * keyB=valueB<br/>
     * keyA1=valueA1<br/>
     * <br/>
     * security_check_code为yjf<br/>
     * <p>
     * 待签名字符串为：<br/>
     * keyA=valueA&keyA1=valueA1&keyB=valueByjf<br/>
     * <b>注意:</b>SIGN_KEY不会被签名
     *
     * @param dataMap
     * @param securityCheckKey 密钥
     * @param de               摘要算法
     * @return
     */
    public static <T> String digest(Map<String, T> dataMap,
                                    String securityCheckKey, DigestALGEnum de, String encoding) {
        if (dataMap == null) {
            throw new IllegalArgumentException("数据不能为空");
        }
        if (securityCheckKey == null) {
            throw new IllegalArgumentException("安全校验码数据不能为空");
        }
        if (de == null) {
            throw new IllegalArgumentException("摘要算法不能为空");
        }
        if (StringUtils.isBlank(encoding)) {
            throw new IllegalArgumentException("字符集不能为空");
        }

        TreeMap<String, T> treeMap = new TreeMap<String, T>(dataMap);
        StringBuilder sb = new StringBuilder(securityCheckKey);
        for (Entry<String, T> entry : treeMap.entrySet()) {
            logger.debug(entry.getKey() + ":" + entry.getValue() + " ; ");
            if (entry.getValue() == null) {
                throw new IllegalArgumentException("待签名值不能为空");
            }
            if (entry.getKey().equals(SIGN_KEY)) {
                continue;
            }
            sb.append(entry.getKey()).append(entry.getValue().toString()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        sb.append(securityCheckKey);

        byte[] toDigest;
        try {
            String str = sb.toString();
            toDigest = str.getBytes(encoding);
            if (logger.isDebugEnabled()) {
                logger.debug("待签名url:" + str);
            }
            MessageDigest md = MessageDigest.getInstance(de.getName());
            md.update(toDigest);
            return new String(Hex.encodeHex(md.digest()));
        } catch (Exception e) {
            throw new RuntimeException("签名失败", e);
        }
    }

    public static <T> String digest(Map<String, T> dataMap, String securityCheckKey) {
        return digest(dataMap, securityCheckKey, DigestALGEnum.MD5, "UTF-8");
    }

    private static String sign(String org) throws Exception {

        String str = org;
        byte[] toDigest = str.getBytes("utf-8");
        if (logger.isDebugEnabled()) {
            logger.debug("待签名url:" + str);
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(toDigest);
        return new String(Hex.encodeHex(md.digest()));

    }

}
