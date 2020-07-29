package com.blog.demo.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blog.demo.entity.Role;

public class SysUsers implements UserDetails{
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.id
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.username
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.password
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_users.profile
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    private String profile;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.id
     *
     * @return the value of sys_users.id
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    private List<Role> roles;
    public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.id
     *
     * @param id the value for sys_users.id
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.username
     *
     * @return the value of sys_users.username
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.username
     *
     * @param username the value for sys_users.username
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.password
     *
     * @return the value of sys_users.password
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.password
     *
     * @param password the value for sys_users.password
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_users.profile
     *
     * @return the value of sys_users.profile
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    public String getProfile() {
        return profile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_users.profile
     *
     * @param profile the value for sys_users.profile
     *
     * @mbg.generated Fri Jul 24 19:02:42 CST 2020
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<SimpleGrantedAuthority> authorities=new ArrayList<SimpleGrantedAuthority>();
		for (Role role:roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public SysUsers(String username,String password,String profile){
		super();
		this.username=username;
		this.password=password;
		this.profile=profile;
	}
}