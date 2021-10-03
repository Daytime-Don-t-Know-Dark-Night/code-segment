package com.boluo.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

	// ShiroFilterFactoryBean
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

		// 设置安全管理器
		bean.setSecurityManager(defaultWebSecurityManager);

		// 添加shiro的内置过滤器
		// anon: 无需认证
		// authc: 必须认证
		// user: 需要记住我
		// perms: 拥有某个资源的权限
		// role: 拥有角色权限
		Map<String, String> filterMap = new LinkedHashMap<>();
		filterMap.put("/user/add", "authc");
		filterMap.put("/user/update", "authc");
		bean.setFilterChainDefinitionMap(filterMap);

		// 设置登录的请求
		bean.setLoginUrl("/toLogin");

		return bean;
	}

	// DefaultWebSecurityManager
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

		// 关联 UserRealm
		securityManager.setRealm(userRealm);
		return securityManager;
	}


	// 创建realm对象, 需要自定义类
	@Bean
	public UserRealm userRealm() {
		return new UserRealm();
	}

}
