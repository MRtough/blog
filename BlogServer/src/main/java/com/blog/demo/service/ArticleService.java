package com.blog.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.blog.demo.entity.Articles;

public interface ArticleService {
	//上传文章内容中的图片
	public List<String> uploadImgInBlog(MultipartFile[] _img); 
	//添加文章 返回文章ID
	public Integer addBlog(Articles blog,MultipartFile imgfile);
	//根据文章ID删除 （用到事务）
	public void deleteBlog(Integer blogId);
	
}
