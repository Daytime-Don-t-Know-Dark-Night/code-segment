package com.boluo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Bean;

public class UserRealm extends AuthorizingRealm {

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("执行了=>授权doGetAuthorizationInfo");

		// 给用户授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("user:add");

		// 获取当前登录的对象
		Subject subject = SecurityUtils.getSubject();
		// User currentUser = (User) subject.getPrincipal();	// 从认证方法中获取user对象, 在返回出来取到
		// info.addStringPermission(currentUser.getPerms());

		return info;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行了=>认证doGetAuthenticationInfo");

		// 从数据库中取出用户名和密码
		String name = "root";
		String password = "123456";

		UsernamePasswordToken userToken = (UsernamePasswordToken) token;

		if (!userToken.getUsername().equals(name)) {
			return null;
		}

		// 在用户登录成功后, 往shiro-session中添加标志, 隐藏登录按钮
		Subject currentSubject = SecurityUtils.getSubject();
		Session session = currentSubject.getSession();
		session.setAttribute("loginUser", "user");

		// 密码认证, shiro处理
		return new SimpleAuthenticationInfo("user", password, "");
	}

	// 整合ShiroDialect: 用来整合 shiro thymeleaf
	@Bean
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
}
