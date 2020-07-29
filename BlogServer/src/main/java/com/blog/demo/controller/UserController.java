package com.blog.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.demo.dao.SysUsersMapper;
import com.blog.demo.dao.UserRoleMapper;
import com.blog.demo.entity.Articles;
import com.blog.demo.entity.SysUsers;
import com.blog.demo.entity.UserRole;
import com.blog.demo.service.UserServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.coobird.thumbnailator.Thumbnails;

@RestController
public class UserController {
	@Autowired
	SysUsersMapper usermapper;
	@Autowired
	UserRoleMapper u_rolemapper;
	@Autowired
	UserServiceImpl userService;
	@GetMapping("/admin/getusers")
	public PageInfo<SysUsers> getuserpage(Integer pageNum) {
		// pageNum:表示第几页 pageSize:表示一页展示的数据
		PageHelper.startPage(pageNum, 10);
		List<SysUsers> listuser = usermapper.selectAll();
		PageInfo<SysUsers> pageInfo = new PageInfo<SysUsers>(listuser);
		return pageInfo;
	}
	//删除用户
	@GetMapping("/admin/deleteUser")
	public HashMap<String, Object> deleteUser(Integer userId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		userService.deleteUser(userId);
		map.put("msg", "刪除成功!");
		return map;
	}

	// 添加用户
	@PostMapping("/admin/addUser")
	public Map<String, Object> addBlog(@RequestParam(value = "username") String username, MultipartFile imgfile,
			@RequestParam(value = "password") String password, @RequestParam(value = "userrole") String userrole
			) throws IllegalStateException, IOException {
		// 文件上传到的保存位置
		File path = new File("src/main/resources/");
		File upload = new File(path.getAbsolutePath(), "static/uploadFile/userProfile/");
		if (!upload.exists()) {
			upload.mkdirs();
		}
		System.out.println("上传路径："+upload);
		// 文件名
		String filename = UUID.randomUUID().toString() + "."+ FilenameUtils.getExtension(imgfile.getOriginalFilename());
		System.out.println(filename);
		imgfile.transferTo(new File(upload, filename));
		SysUsers user=new SysUsers(username, password, filename);
		int numback=usermapper.insert(user);
		Integer userId=user.getId();
		System.out.println("返回的用户ID："+userId);
		//插入用户角色信息
		if (userrole.equals("管理员")) {
			List<UserRole> list=new ArrayList<UserRole>();
			list.add(new UserRole(userId, 1));
			list.add(new UserRole(userId, 2));
			int numback1=u_rolemapper.insert(list);
		}else if (userrole.equals("普通用户")) {
			List<UserRole> list=new ArrayList<UserRole>();
			list.add(new UserRole(userId, 2));
			int numback1=u_rolemapper.insert(list);
		}else {
			System.out.println("角色插入错误！");
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "添加用户成功!");
		return map;
	}

	// 测试
	@RequestMapping(value = "/getusers/{pageNum}")
	public PageInfo<SysUsers> getusers(@PathVariable("pageNum") int pageNum) {
		// pageNum:表示第几页 pageSize:表示一页展示的数据
		PageHelper.startPage(pageNum, 3);
		List<SysUsers> listuser = usermapper.selectAll();
		PageInfo<SysUsers> pageInfo = new PageInfo<SysUsers>(listuser);
		return pageInfo;
	}
}
