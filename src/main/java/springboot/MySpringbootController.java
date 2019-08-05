package springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.entity.MyUser;
import springboot.service.MybatisServiceTest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/springboot")
//RestController 标注该类所有的请求方法返回都为json格式，不做视图解析
@RestController
public class MySpringbootController {
    @Autowired
    MybatisServiceTest mybatisServiceTest;
    @RequestMapping("/test")
    public String getTest(){
        return "successs";
    }
    @RequestMapping("/testList")
    public List<String> getTestList(){
        return new ArrayList<String>(){{
            add("a");
            add("a");
        }};
    }
    @RequestMapping("/error")
    public void getTestError(HttpServletRequest request){
        String id = request.getParameter("id");
        Integer.parseInt(id);
    }
    @RequestMapping("/getUser/{id}")
    public MyUser getUser(@PathVariable("id") String id){
       return mybatisServiceTest.selectAllById(id);
    }
    @RequestMapping("/getUser")
    public MyUser getUser(){
        return mybatisServiceTest.selectAll();
    }
}
