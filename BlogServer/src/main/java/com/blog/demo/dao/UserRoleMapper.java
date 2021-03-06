package com.blog.demo.dao;

import com.blog.demo.entity.UserRole;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
	int deleteByuserId(Integer userId);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_role
	 * @mbg.generated  Fri Jul 24 19:03:31 CST 2020
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_role
	 * @mbg.generated  Fri Jul 24 19:03:31 CST 2020
	 */
	//批量插入
	int insert(@Param("u_roles") List<UserRole> record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_role
	 * @mbg.generated  Fri Jul 24 19:03:31 CST 2020
	 */
	UserRole selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_role
	 * @mbg.generated  Fri Jul 24 19:03:31 CST 2020
	 */
	List<UserRole> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_role
	 * @mbg.generated  Fri Jul 24 19:03:31 CST 2020
	 */
	int updateByPrimaryKey(UserRole record);
}