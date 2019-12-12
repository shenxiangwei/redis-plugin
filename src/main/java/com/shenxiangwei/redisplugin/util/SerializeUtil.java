package com.shenxiangwei.redisplugin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * <br>
 * 标题: 序列化工具类 <br>
 * 描述: <br>
 *
 * @author shenxiangwei
 * @date 2019/12/11 5:28 下午
 */
public class SerializeUtil {

    private final static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    /**
     * 反序列化：将字节数组转成java对象
     * @param value byte数组
     * @return Object
     * @author shenxiangwei
     * @date 2019/12/11 5:33 下午 */
    public static Object bytesToObject(byte[] value){
        ByteArrayInputStream bytesIn = null;
        ObjectInputStream in = null;
        try {
            bytesIn = new ByteArrayInputStream(value);
            in = new ObjectInputStream(bytesIn);
            Object obj = in.readObject();
            return obj;
        } catch (Exception ex) {
            logger.error("序列化失败",ex);
            return null;
        } finally {
            try {
                assert bytesIn != null;
                bytesIn.close();
                assert in != null;
                in.close();
            } catch (IOException e) {
                logger.error("io异常",e);
            }
        }
    }

    /**
     * 序列化，将Java对象转换成字节数组
     * @param obj 序列化对象
     * @return byte数组
     * @author shenxiangwei
     * @date 2019/12/11 5:31 下午 */
    public static byte[] objectToBytes(Object obj){
        ByteArrayOutputStream bytesOut = null;
        ObjectOutputStream out = null;
        try {
            bytesOut = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bytesOut);
            out.writeObject(obj);
            //序列化，将obj写入ByteArray数组流中
            return bytesOut.toByteArray();
        } catch (Exception ex) {
            logger.error("反序列化失败",ex);
            return null;
        } finally {
            try {
                assert bytesOut != null;
                bytesOut.close();
                assert out != null;
                out.close();
            } catch (IOException e) {
                logger.error("io异常",e);
            }

        }
    }
}
