package com.boluo.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm {

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("执行了=>授权doGetAuthorizationInfo");
		return null;
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
		return new SimpleAuthenticationInfo("", password, "");
	}
}
