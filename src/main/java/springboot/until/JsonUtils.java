package springboot.until;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * json工具
 *
 * @author liliping
 * @version 1.0.0
 * @className JsonUtils
 * @date 2018年12月28日
 */
public class JsonUtils {
    /**
     * json 对象转map
     * @param json
     * @return
     */
    public static Map<String,Object> jsonToMap(String json){
        return JSON.parseObject(json, Map.class);
    }
    /**
     * listMap对象转ListJSONObject
     * @param list
     * @return
     */
    public static  List<JSONObject> listMapToListJSONObject(List<Map<String, Object>> list){
        List<JSONObject> arry = new ArrayList<>();
        list.forEach((obj)->{
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(obj));
            arry.add(json);
        });
        return  arry;
    }
    /**
     * 对象转json
     * @param obj
     * @return
     */
    public static String objectToJson(Object obj){
        return JSON.toJSONString(obj);
    }
    /**
     * json字符串转对象
     * @param strjson
     * @param clazz
     * @return
     */
    public static <T> T  jsonToObject(String strjson, Class<T> clazz){
        return  JSON.parseObject(strjson, clazz);
    }
    /**
     * json转List
     * @param json
     * @param clazz
     * @return
     */
    public  static <T> List<T>  jsonToList(String json, Class<T> clazz){
        return JSON.parseArray(json,clazz);
    }
    /**
     * jsonArray转List
     * @param jsonArray
     * @param clazz
     * @return
     */
    public  static <T> List<T>  jsonToList(JSONArray jsonArray, Class<T> clazz){
        String jsonStr = JSONObject.toJSONString(jsonArray);
        return JSONObject.parseArray(jsonStr,  clazz);
    }

}
