package com.ctgu.fy.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ctgu.fy.entity.Student;
import com.ctgu.fy.entity.User;
import com.ctgu.fy.service.StudentService;
import com.ctgu.fy.service.UserService;
import com.ctgu.fy.util.CpachaUtil;

/**
 * ϵͳ��ҳ������
 * @author fy
 *
 */

@RequestMapping("/system")   //�������ӷ������Ǹ���url��system���棬û�����ڸ�Ŀ¼�£�������������
@Controller
public class SystemController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/index",method=RequestMethod.GET) //������ע�ͱ�ʾ·������Ҫ����system����
	public ModelAndView index(ModelAndView model){
		model.setViewName("system/index"); //model����springmvc�������õ���ͼ������ȥ��
		return model;
	}
	
	/**
	 * ��½ҳ��
	 */
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public ModelAndView login(ModelAndView model){
		model.setViewName("system/login");
		return model;
	}
	
	/**
	 * ע����¼ 
	 */
	@RequestMapping(value = "/login_out",method=RequestMethod.GET)
	public String loginOut(HttpServletRequest request){
		request.getSession().setAttribute("user", null);
		return "redirect:login";
	}
	
	/**
	 * ��¼���ύ
	 */
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	@ResponseBody  //��MAP<>ת��json����
	public Map<String, String> login(
			@RequestParam(value="username",required=true) String username,
			@RequestParam(value="password",required=true) String password,
			@RequestParam(value="vcode",required=true) String vcode,
			@RequestParam(value="type",required=true) int type,
			HttpServletRequest request
			){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(username)){
			ret.put("type", "error");
			ret.put("msg", "�û�������Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(password)){
			ret.put("type", "error");
			ret.put("msg", "���벻��Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(vcode)){
			ret.put("type", "error");
			ret.put("msg", "��֤�벻��Ϊ��!");
			return ret;
		}
		String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");
		if(StringUtils.isEmpty(loginCpacha)){
			ret.put("type", "error");
			ret.put("msg", "��ʱ��δ�������Ự��ʧЧ����ˢ�º�����!");
			return ret;
		}
		if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){
			ret.put("type", "error");
			ret.put("msg", "��֤�����!");
			return ret;
		}
		request.getSession().setAttribute("loginCpacha", null);
		//�����ݿ���ȥ�����û�
		if(type == 1){
			//����Ա
			User user = userService.findByUserName(username);
			if(user == null){
				ret.put("type", "error");
				ret.put("msg", "�����ڸ��û�!");
				return ret;
			}
			if(!password.equals(user.getPassword())){
				ret.put("type", "error");
				ret.put("msg", "�������!");
				return ret;
			}
			request.getSession().setAttribute("user", user);
		}
		if(type == 2){
			//ѧ��
			Student student = studentService.findByUserName(username);
			if(student == null){
				ret.put("type", "error");
				ret.put("msg", "�����ڸ�ѧ��!");
				return ret;
			}
			if(!password.equals(student.getPassword())){
				ret.put("type", "error");
				ret.put("msg", "�������!");
				return ret;
			}
			request.getSession().setAttribute("user", student);
		}
		request.getSession().setAttribute("userType", type);
		ret.put("type", "success");
		ret.put("msg", "��¼�ɹ�!");
		return ret;
	}
	
	/**
	 * ��ʾ ��֤��
	 */
	@RequestMapping(value="/get_cpacha",method=RequestMethod.GET)
	public void getCpacha(HttpServletRequest request,
			@RequestParam(value="vl",defaultValue="4",required=false) Integer vl,
			@RequestParam(value="w",defaultValue="98",required=false) Integer w,
			@RequestParam(value="h",defaultValue="33",required=false) Integer h,
			HttpServletResponse response){
		CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
		String generatorVCode = cpachaUtil.generatorVCode();
		request.getSession().setAttribute("loginCpacha", generatorVCode);
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}