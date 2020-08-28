package com.major;

import com.major.domain.User;
import com.major.dao.UserDAO;
import com.major.service.TheCipher;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.Key;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
public class test {





        InputStream in;
        SqlSessionFactoryBuilder builder;
        SqlSessionFactory factory;
        SqlSession seesion;
        UserDAO userDAO;






@Test

public  void jdkAES() {
    try {String password = "xiehuaxin";

        //1.生成KEY

        byte[] byteKey = "1234561234561234".getBytes("UTF-8");


        //2.转换KEY
        Key key = new SecretKeySpec(byteKey,"AES");

        //3.加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(password.getBytes());
        System.out.println("加密后：" + Hex.encodeHexString(result));

        //4.解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        result = cipher.doFinal(result);
        System.out.println("解密后：" + new String(result));
    } catch (Exception e) {
        e.printStackTrace();
    }
}




@Test

public  void jdkAES2() {
    try {
        TheCipher theCipher=new TheCipher();

        String username = "stone6";
        String token="913701de22850ab1a2daad8d6868f229";

        String username2= theCipher.decrypy(token);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

@Test
        public void test_selectAll(){

        List<User> users=userDAO.selectAll();//把user表中获取所有对象

        for(User user:users){
            System.out.println("第一次："+ user.toString());
        }//打印


    }

 @Test
            public void test_selectByID(){
            User id_u=userDAO.findUserByID(1);
            System.out.println(id_u);

    }

@Test
    public void test_selectByName(){
        User id_u=userDAO.findUserByName("stone8888888");
        System.out.println(id_u);

    }
 @Test
    public void test_selectByRange(){
        List<User> id_u=userDAO.findUserByRange(1,3);
        System.out.println(id_u.toString());

    }



    @Test
            public void test_insertUser(){
        ApplicationContext ac=new AnnotationConfigApplicationContext(Configuration.class);
        User got_user =(User) ac.getBean("user");
        got_user.setUsername("rabbit");
        got_user.setPassword("778554");
        got_user.setCollege("JLU");
        got_user.setEmail("494484@qq.com");
        got_user.setCall("135488156141");
        userDAO.insertUser(got_user);//把对象insert进user表中

    }


    @Test
    public void test_updateUserByID(){

        ApplicationContext ac=new AnnotationConfigApplicationContext(Configuration.class);
        User got_user =(User) ac.getBean("user");
        got_user.setUsername("mengshi");
        got_user.setPassword("778554");
        got_user.setCollege("JLUUUUUUUU");
        got_user.setEmail("494484@qq.com");
        got_user.setCall("135488156141");
        got_user.setId(1);
        userDAO.updateUserByID(got_user);//把对象insert进user表中

    }



















    }

