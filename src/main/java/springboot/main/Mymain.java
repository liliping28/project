package springboot.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//ComponentScan 可以解决根类或者配，告诉Spring哪个package的用注解标识的类会被spring自动扫描并且装入bean容器
//默认情况下是加载和Application类所在同一个目录下的所有类，包括所有子目录下的类
@ComponentScan(basePackages = {"springboot"})
@MapperScan(basePackages = {"springboot.repository"})
@SpringBootApplication
public class Mymain {
    public static void main(String[] args) {
        SpringApplication.run(Mymain.class, args);
    }
}
