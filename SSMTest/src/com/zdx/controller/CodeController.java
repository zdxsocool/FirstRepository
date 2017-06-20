package com.zdx.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 控制验证码的controller
 */
@Controller
public class CodeController {
    private int width = 90;//验证码宽度
    private int height = 40;//验证码高度
    private int codeCount = 4;//验证码个数
    private int lineCount = 19;//混淆线个数

    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * 具体获取验证码的方法
     * @param time  time为时戳,这样的话可以避免浏览器缓存验证码
     * @throws IOException
     */
    @RequestMapping(value = "/code")//,method = RequestMethod.GET
    public void getCode( @RequestParam("time")String time, HttpServletRequest request,
                        HttpServletResponse response) throws IOException{//@PathVariable("time")
        //定义随机数类
        Random r = new Random();
        //定义存储验证码的类
        StringBuilder builderCode = new StringBuilder();
        //定义画布
        BufferedImage buffImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //得到画笔
        Graphics g = buffImg.getGraphics();
        //1.设置颜色,画边框
        g.setColor(Color.black);
        g.drawRect(0,0,width,height);
        //2.设置颜色,填充内部
        g.setColor(Color.white);
        g.fillRect(1,1,width-2,height-2);
        //3.设置干扰线
        g.setColor(Color.gray);
        for (int i = 0; i < lineCount; i++) {
            g.drawLine(r.nextInt(width),r.nextInt(width),r.nextInt(width),r.nextInt(width));
        }
        //4.设置验证码
        g.setColor(Color.blue);
        //4.1设置验证码字体
        g.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
        for (int i = 0; i < codeCount; i++) {
            char c = codeSequence[r.nextInt(codeSequence.length)];
            builderCode.append(c);
            g.drawString(c+"",15*(i+1),15);
        }
        //5.输出到屏幕
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(buffImg,"png",sos);
        //6.保存到session中
        HttpSession session = request.getSession();
        session.setAttribute("codeValidate",builderCode.toString());
        //7.禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        //8.关闭sos
        sos.close();
    }
    /**
     * 用户登录控制
     * @param user
     * @param attr 重定向传参数
     * @param session
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public String login(String codevalidate, HttpSession session, HttpServletResponse response) throws IOException {
        /*if (user.getUsername() == null || user.getPassword() == null){
            attr.addFlashAttribute("error","请输入用户名或密码");
            return "redirect:/login";
        }
        //去掉空格
        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        //添加到要显示的信息中
        attr.addFlashAttribute("username",user.getUsername().trim());*/
        //验证码验证
        String code = (String) session.getAttribute("codeValidate");
        if (codevalidate == null || !codevalidate.equalsIgnoreCase(code)){
        	return "No!!!";
        }
        return "Yes!";
        /*//用户名密码错误
        user.setPassword(DecriptUtil.SHA1(user.getPassword().trim()));
        user = userDao.findUserByLogin(user);
        if (user == null){
            attr.addFlashAttribute("error","用户名或密码错误");
            return "redirect:/login";
        }*/
        //登录成功加入session
       // session.setAttribute("userLogin",user);
        //更新登录时间
       // userDao.updateDateById(user.getId());
        //跳转到用户主页
        //return "redirect:/user/"+user.getId();
    }
}