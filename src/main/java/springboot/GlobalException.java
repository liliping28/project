package springboot;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * springboot 全局异常类
 */
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(RuntimeException.class)
    /**
     * 异常统一返回方法
     */
    @ResponseBody
    public Map<String,Object> exceptionHandler(){
        Map<String,Object> map = new HashMap<>();
        map.put("errorCode",500);
        map.put("errorMessage","系统异常");
        return  map;
    }

}
