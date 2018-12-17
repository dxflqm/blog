package org.panzhi.blog.weback.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.panzhi.blog.common.annotation.Controller;
import org.panzhi.blog.common.annotation.RequestMapping;
import org.panzhi.blog.common.annotation.RequestParam;
import org.panzhi.blog.common.constant.SysConstant;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.error.WebackErrorMsg;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.common.result.BuildResponseMsg;
import org.panzhi.blog.common.servlet.util.SessionUtil;
import org.panzhi.blog.common.util.Assert;
import org.panzhi.blog.weback.util.SysResouceReadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录信息控制层
 * @RequestParam 路径
 * @author panzhi
 * @version v1.0, 2018.8.7
 */
@Controller
@RequestMapping("/common")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private int width =120;
	
	private int height = 45;
	
	private int rgbcount = 256;
	
	@RequestMapping(value="/codeServlet")
	public void codeServlet(HttpServletRequest request,HttpServletResponse response){
		try {
			//设置图片的相应格式
			response.setContentType("image/png");
			//设置浏览器不要缓存此图片
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", -1);
			//设置图片的缓冲流
			BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
			//创建一个画布，并获取画布
			Graphics g=image.getGraphics();
			//设置字体的大小
			g.setFont(new Font("微软雅黑",Font.BOLD,30));
			g.setColor(new Color(0xDCDCDC));
			g.fillRect(0, 0, width, height);
			//设置char数组
			char[] words="fheiwq3erty6098hjgcvhdvjk43xcvbn24jlf93671rtyuih45456erhfj".toCharArray();
			String code="";
			//创建一个随机数对象
			Random random=new Random();		
			for(int i=0;i<4;i++){
				g.setColor(new Color(random.nextInt(rgbcount),random.nextInt(rgbcount),random.nextInt(rgbcount)));
				//随机的获取字符数组中的元素
				String word=words[random.nextInt(words.length)]+"";
				code+=word;
				//将元素放入画布中
				g.drawString(word, i*25+15, 30 + random.nextInt(5));
			}
			for(int i=0;i<30;i++){
			    int x=(int)(Math.random()*width);
			    int y=(int)(Math.random()*height);
			    int red=(int)(Math.random()*255);
			    int green=(int)(Math.random()*255);
			    int blue=(int)(Math.random()*255);
			    g.setColor(new Color(red,green,blue));
			    g.drawOval(x, y, 1, 3);
			}
			g.dispose();
			SessionUtil.createSessionKV(request, "imagecode", code);
			OutputStream out=response.getOutputStream();
			//将文件输出
			ImageIO.write(image, "png", out);
			out.close();
		} catch (IOException e) {
			logger.error("用户登陆模块，获取动态验证码异常!", e);
		}
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("userEmail") String userEmail,
			@RequestParam("userPwd") String userPwd,
			@RequestParam("imageCode") String imageCode){
		//效验
		try {
			Assert.isEmpty(userEmail, "用户名不能为空");
			Assert.isEmpty(userPwd, "密码不能为空");
			Assert.isEmpty(imageCode, "验证码不能为空");
			//效验验证码
			if(!SessionUtil.checkSessionKV(request, "imagecode") || !imageCode.equals(SessionUtil.getSessionKV(request, "imagecode"))) {
				return BuildResponseMsg.buildFailMsgNoData(WebackErrorMsg.IMAGECODE_ERROR);
			}
			SessionUtil.removeSessionKV(request, "imagecode");
			String sysName = SysResouceReadUtil.get("user");
			String sysPwd = SysResouceReadUtil.get("pwd");
			if(!sysName.equals(userEmail) || !sysPwd.equals(userPwd)){
				return BuildResponseMsg.buildFailMsgNoData(WebackErrorMsg.ACCOUNT_ERROR);
			}
			SessionUtil.createSessionKV(request, "login", "true");
			return BuildResponseMsg.buildSuccessMsgNoData();
		} catch (CommonException e) {
			logger.error("标签控制层:新增、修改标签信息异常,请求接口：addOrEdit",e);
			return BuildResponseMsg.buildCommonFailMsg(e.getErrMsg());
		}
	}
	
	/**
	 * 用户退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		SessionUtil.removeAllSessionKV(request);
		return BuildResponseMsg.buildSuccessMsgNoData();
	}
	
	/**
	 * 检查用户是否登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkUser")
	public String checkUser(HttpServletRequest request, HttpServletResponse response){
		if(!SessionUtil.checkSessionKV(request, "login")){
			return BuildResponseMsg.buildFailMsgNoData(CommonErrorMsg.REQUEST_ERROR);
		}
		return BuildResponseMsg.buildSuccessMsgNoData();
	}
	
	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @param uploadName
	 */
	@RequestMapping(value="/download")
	public void download(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("file") String uploadName) {
		try {
			if(StringUtils.isNotEmpty(uploadName)) {
				String filePath = SysConstant.SERVER_UPLOAD_ADS + File.separatorChar + uploadName;
				File file = new File(filePath);
				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					String filename = URLEncoder.encode(file.getName(), "utf-8"); // 解决中文文件名下载后乱码的问题
					response.setContentType("application/octet-stream;charset=UTF-8");
					response.setHeader("Content-Disposition", "attachment; filename=" + filename + "");
					OutputStream out = new BufferedOutputStream(response.getOutputStream());
					byte[] b = new byte[2048];
					int length;
					while ((length = fis.read(b)) > 0) {
						out.write(b, 0, length);
					}
					out.flush();
					out.close();
					fis.close();
				}
			}
		} catch (IOException e) {
			logger.error("文章控制层:下载内容信息异常",e);
		}
	}

}
