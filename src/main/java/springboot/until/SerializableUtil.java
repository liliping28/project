package springboot.until;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @ClassName: SerializableUtil
 * @Description: 序列化工具类
 * @author: liliping
 * @date: 2019/10/17 14:32
 * @Version 1.0
 */
public class SerializableUtil {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SerializableUtil.class);

    /**
     * 序列化对象方法
     * @param object 序列化对象
     * @return byte[] 刷入对象的字节数组（字节数组）
     */
    public  static byte[] serializable(Object object){
        byte[] bys = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            //1.初始化字节输出流
            bos = new ByteArrayOutputStream();
            //2.初始化对象输出流，并且指定将写入的字节输出流
            oos = new ObjectOutputStream(bos);
            //3.将对象写到对象输出流，对象输出流在刷新到字节输出流中
            oos.writeObject(object);
            //4.获取字节输出流中刷入的字节数组
            bys = bos.toByteArray();
        } catch (Exception e) {
            log.error("serializable ERROR", e);
        }finally {
            //5.关闭流管道
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(oos);
        }
        return bys;
    }
    /**
     * 反序列化对象方法
     * @param bys 刷入对象的字节数组（字节数组）
     * @return object 反序列化对象
     */
    public  static Object unSerializable(byte[] bys){
        Object object = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            //1.,传入对象字节数组，并且初始化字节输入流
            bis = new ByteArrayInputStream(bys);
            //2.初始化对象输入流，并且指定将读出的字节输入流
            ois = new ObjectInputStream(bis);
            //3.通过对象输入流读出字节输入流中的字节，并且转成对象
            object = ois.readObject();
        } catch (Exception e) {
            log.error("serializable ERROR", e);
        }finally {
            //4.关闭流管道
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(ois);
        }
        return object;
    }

    /**
     * 获取已知的对象类的反序列化对象
     * @param bys 刷入对象的字节数组（字节数组）
     * @return clazz 反序列化强转类型
     */
    public static <T>T unSerializable(byte[] bys ,Class<T> clazz){
        return (T)unSerializable(bys);
    }
    /**
     * 对象拷呗，包括包装类复制
     */
    public static Object clone(Serializable t){
        return  SerializationUtils.clone(t);
    }
}
