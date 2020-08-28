package com.major.dao;

import com.major.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserDAO {
    @Select("select * from  user ;")
    List<User> selectAll();//选全部

    @Select("select * from user where id=#{id};")
    User findUserByID(int id);//选特定id返回（ID为自增加索引【在PHPmyadmin 中设置就可以】）

    @Select("select * from user where username=#{username}")
    User findUserByName(String username);//返回特定用户名的

    @Select("select * from user where id between #{start_id} and #{end_id} ;")
    List<User> findUserByRange(@Param("start_id")int start_id,@Param("end_id") int end_id);


//以上为检索部分





@Insert("insert into user(username,password,college,phonecall,email)values(#{username},#{password},#{college},#{phonecall},#{email}) ;")
void insertUser(User user);//插入新的用户，id为MYSQL自动决定


@Update("update user set username=#{username},password=#{password},college=#{college},phonecall=#{phonecall},email=#{email} where id=#{id};")
void updateUserByID(User user);//根据ID来更新user，不过要首先set本地user的ID！
    @Update("update user set college=#{college},phonecall=#{phonecall},email=#{email} where username=#{username};")
    void updateUserByName(User user);//根据ID来更新user，不过要首先set本地user的ID！

    @Update("update user set secretKey=#{secretKey} where username=#{username};")
    void updateUserSecretKeyByName(User user);//根据名字更新密钥便于取回密钥进行解密




@Delete("delete from user where id=#{id}")
    void deleteByID(int id);//根据ID来删除




}
