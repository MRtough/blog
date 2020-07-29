package com.blog.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.demo.dao.SysUsersMapper;
import com.blog.demo.dao.UserRoleMapper;
import com.blog.demo.entity.SysUsers;

@Service
public class UserServiceImpl implements UserDetailsService{
	@Autowired
	SysUsersMapper userMapper;
	@Autowired
	UserRoleMapper u_rolemapper;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		SysUsers user=userMapper.loadUserByUsername(username);
		if (user==null) {
			throw new UsernameNotFoundException("账户不存在");
		}
		user.setRoles(userMapper.getUserRolesByUid(user.getId()));
		return user;
	}
	@Transactional
	public void deleteUser(Integer userId) {
		int numback = userMapper.deleteByPrimaryKey(userId);
		int numback1 = u_rolemapper.deleteByuserId(userId);
	}

}
