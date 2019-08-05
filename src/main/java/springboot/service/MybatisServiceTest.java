package springboot.service;

import springboot.entity.MyUser;

public interface MybatisServiceTest {
    MyUser selectAllById(String id);
    MyUser selectAll();
}
