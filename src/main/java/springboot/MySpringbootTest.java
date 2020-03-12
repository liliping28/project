
package springboot;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springboot.entity.MyUser;
import springboot.main.Mymain;
import springboot.service.MybatisServiceTest;
import springboot.until.PropertiesUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Mymain.class)
public class MySpringbootTest {
    private static final Log logger = LogFactory.getLog(MySpringbootTest.class);
    @Autowired
    MybatisServiceTest mybatisServiceTest;
    @Autowired
    private PropertiesUtil propertiesUtil;
    private MockMvc mockMvc ;
    @Test
    public void getUser(){
        MyUser myUser = mybatisServiceTest.selectAll();
        logger.info(JSON.toJSON(myUser));
    }
    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(new MySpringbootController()).build();
    }
    @Test
    public void testGetUser() throws Exception {
        System.out.println(propertiesUtil.getString("spring.profiles.active"));
        String myUser = mockMvc.perform(MockMvcRequestBuilders.get("/getUser")).andReturn().getResponse().getContentAsString();
        logger.info(JSON.toJSON(myUser));
    }

}

