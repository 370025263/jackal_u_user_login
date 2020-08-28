package com.major.controller;

import com.google.gson.Gson;
import com.major.Configuration;
import com.major.domain.User;
import com.major.dao.UserDAO;
import com.major.service.TheCipher;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping(path = "/user")
public class UserServlet {
    @Autowired
    Gson gson;
   @Autowired
    private UserDAO dao ;//这里自动注入DAO

    @Autowired
    TheCipher cp;//自动注入破解器，花费30分钟，似乎不能再利用AnnotaiongApplicationContext来引入bean了。

    @RequestMapping(path = "/register",params = {"username"})
    public void register(User user , HttpServletRequest request, HttpServletResponse response) throws IOException {



        System.out.println("收到的username是："+user.getUsername());
        System.out.println("收到的Password是："+user.getPassword());
        User got_user=dao.findUserByName(user.getUsername());
        System.out.println("已经得到了从数据库得到了DAO");
        System.out.println("got_user :"+got_user);
        if(got_user==null&user.getPassword().equals("")){
            response.setContentType("text/javascript;");
            PrintWriter out = response.getWriter();
            out.println("0");return;}//如果是新用户，并且请求参数中没有密码，说明是验证有效性的，返回0；

        if(got_user!=null){
            response.setContentType("text/javascript;");
            PrintWriter out = response.getWriter();
            out.println("-1");return;}//如果已经存在账户了，那么返回-1告诉客户端用户已经被注册。

        dao.insertUser(user);//正常的有 正常name，有密码，就进行写入并且返回1。
        response.setContentType("text/javascript;");
        PrintWriter out = response.getWriter();
        out.println("1");}



    @RequestMapping(path = "/login",params = {"username","password"})
    public void login(User user ,HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        System.out.println("收到的username是："+user.getUsername());
        System.out.println("收到的Password是："+user.getPassword());
        User got_user=dao.findUserByName(user.getUsername());
        System.out.println("已经得到了从数据库得到了DAO");
        System.out.println("got_user :"+got_user);

        if(got_user==null){
            response.setContentType("text/javascript;");
            PrintWriter out = response.getWriter();
            out.println("-1");return;}//从dao只能得到空指针，说明用户不存在，返回-1
        System.out.println("数据库中得到的密码为："+got_user.getPassword());
        System.out.println("接收到的密码为："+user.getPassword());
        if(!got_user.getPassword().equals(user.getPassword())){
            response.setContentType("text/javascript;");
            PrintWriter out = response.getWriter();
            out.println("0");return;
        }//密码不匹配，返回0，表示密码错误。

        String token=cp.encrypt(user.getUsername());


        //对用户名使用密钥来加密，生成token
        Cookie cookie=new Cookie("token", token);
        cookie.setMaxAge(3600*24*60);
        cookie.setDomain(request.getServerName());//cookie有同源策略，如果不指定cookie域，就无法设置成功。
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
        //返回有效期15天的本地页面cookie
        response.setContentType("text/javascript;");
        PrintWriter out = response.getWriter();
        out.println(token);return;
        //返回1表示登陆成功

    }

    @RequestMapping(path = "/logout")
    public void logout(User user ,HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        //将客户的cookie进行删除。
        System.out.println("客户端响应已经收到");

        Cookie userCookie=new Cookie("token", null);
        userCookie.setMaxAge(0); //立即删除型
        userCookie.setDomain(request.getServerName());

        userCookie.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
        userCookie.setHttpOnly(false);
        response.addCookie(userCookie);//将cookie返回。
        response.setContentType("text");
        PrintWriter pw=response.getWriter();
        pw.println("cookie has been set to null");
    }


    @RequestMapping(path ="/update" ,method = RequestMethod.POST)
    public void update(User user ,HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("客户端更新请求已经收到:"+user.toString());

        dao.updateUserByName(user);//更新操作
        User user1=dao.findUserByName(user.getUsername());
        System.out.println("现在表中内容:"+user1.toString());

        response.setContentType("text");
        PrintWriter pw=response.getWriter();
        pw.println("1");


    }


    @RequestMapping(path = "/userCookieValidate",params = {"cookie"})
    public void userCookieValidate(String cookie, HttpServletRequest request, HttpServletResponse response) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, DecoderException, InvalidKeyException {


//这里，从cookie中获得的值都会再末尾加换行符，看起来一模一样，但是最后多两个字节。equal是false的，所以这里必须.trim()去掉多余的空格和换行符！浪费了我50分钟！
        String username= cp.decrypy(cookie.trim());

        //将cookie解密成明文
        System.out.println("明文为："+username);
        User user=dao.findUserByName(username);
        //从数据库中获得user

        if(user==null){return;}
        //如果没有结果，说明是无效token，直接结束。 response.setContentType("json");
        //        PrintWriter out = response.getWriter();
        //        out.println("null");先试一试，如果直接return；前端收到的是null，那么就可以不该，不然必须加提示符！

        String json_user=gson.toJson(user);
        response.setContentType("application/json;");
        PrintWriter out = response.getWriter();
        out.println(json_user);}
    //有效token 返回user对象user ，便于前端准备页面。












}

















