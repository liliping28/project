package springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.entity.MyUser;
import springboot.repository.MybatisRepositoryTest;
import springboot.service.MybatisServiceTest;

/**
 * mybatis测试
 */
@Service
public class MybatisServiceTestImpl implements MybatisServiceTest {
    @Autowired
    private MybatisRepositoryTest mybatisRepositoryTest;
    @Override
    public MyUser selectAllById(String id) {
        return mybatisRepositoryTest.selectAllById(id);
    }
    @Override
    public MyUser selectAll() {
        try {
            return mybatisRepositoryTest.selectAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
