package com.boluo.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

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

		// 密码认证, shiro处理
		return new SimpleAuthenticationInfo("user", password, "");
	}
}
