package springboot.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import springboot.entity.MyUser;
public interface MybatisRepositoryTest {
   /**
    * 在用#作为参数时。在解析sql时，会默认在为每个参数在参数的前后个追加了'符号。这样可以防止sql注入。
    * 作为参数时,条件查询强制用#，表名替换用$这个符号
    * @param id
    * @return
    */
   @Select("select * from my_user where id =#{id}")
   MyUser selectAllById(String id);

   /**
    * $在解析sql时直接替换成值，用户可以在输入值的后面追加or 1=1这种情况，是非常危险的。
    * 因为在$解析sql时sql直接替换成用户输入的内容了。如果用$这种情况作为参数时，在替换表名时可以。
    * @param id
    * @return
    */
   //@Select("select * from my_user where id =${id}")
   //MyUser selectAllById(@Param("id") String );
   @Select("select * from my_user")
   MyUser selectAll();
}
