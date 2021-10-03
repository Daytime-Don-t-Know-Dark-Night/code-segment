package com.boluo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

	@RequestMapping({"/", "/index", "index.html"})
	public String toIndex(Model model) {
		model.addAttribute("msg", "hello, Shiro");
		return "index";
	}

	@RequestMapping("/user/add")
	public String add() {
		return "user/add";
	}

	@RequestMapping("/user/update")
	public String update() {
		return "user/update";
	}

	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login";
	}

	@RequestMapping("/login")
	public String login(String username, String password, Model model) {

		// 获取当前的用户
		Subject subject = SecurityUtils.getSubject();

		// 封装用户的登录数据
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		try {
			// 执行登录方法, 没有异常说明鉴权通过
			subject.login(token);
			return "index";
		} catch (UnknownAccountException e) {
			model.addAttribute("msg", "用户名不存在!!!");
			return "login";
		} catch (IncorrectCredentialsException e) {
			model.addAttribute("msg", "密码不正确!!!");
			return "login";
		}
	}

}
