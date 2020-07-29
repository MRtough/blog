package com.blog.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.demo.dao.ArticleLabelMapper;
import com.blog.demo.dao.ArticlesMapper;
import com.blog.demo.dao.CommentsMapper;
import com.blog.demo.dao.LabelsMapper;
import com.blog.demo.dao.MessageMapper;
import com.blog.demo.entity.ArticleIndexAdmin;
import com.blog.demo.entity.Articles;
import com.blog.demo.entity.BlogIndex;
import com.blog.demo.service.ArticleServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.coobird.thumbnailator.Thumbnails;

@RestController
public class ArticleController {

	@Autowired
	MessageMapper messagemapper;
	@Autowired
	ArticlesMapper articlemapper;
	@Autowired
	LabelsMapper labelsmapper;
	@Autowired
	ArticleLabelMapper artlabmapper;
	@Autowired
	CommentsMapper commentsmapper;
	@Autowired
	ArticleServiceImpl blogService;

	// 上傳文章中的圖片
	@PostMapping("/admin/uploadImgInBlog")
	public HashMap<String, Object> Upload(MultipartFile[] _img) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> list = blogService.uploadImgInBlog(_img);
		map.put("msg", "文章图片上传成功！");
		map.put("list", list);
		return map;
	}

	// 添加博文
	@PostMapping("/admin/addArticle")
	public Map<String, Object> addBlog(@RequestParam(value = "blogtitle") String blogtitle,
			@RequestParam(value = "blogsort") String blogsort, MultipartFile imgfile,
			@RequestParam(value = "blogcontent") String blogcontent,
			@RequestParam(value = "blogstatus") String blogstatus, @RequestParam(value = "userid") Integer userid) {
		Articles article = new Articles();
		article.setSysUserId(userid);
		article.setArticleTitle(blogtitle);
		if (blogsort.equals("个人博客")) {
			article.setSortId(1);
		} else {
			article.setSortId(2);
		}
		article.setArticleContent(blogcontent);
		Date data = new Date();
		article.setArticleDate(data);
		article.setPublishStatus(blogstatus);
		Integer backBlogId=blogService.addBlog(article, imgfile);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "添加文章成功!");
		// 测试插入文章后返回文章的自增ID
		System.out.println("文章Id:" + backBlogId);
		map.put("backBlogId", backBlogId);
		return map;
	}

	// 分頁查詢文章索引
	@GetMapping("/allArticle")
	public PageInfo<ArticleIndexAdmin> getAllBlogIndex(
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<ArticleIndexAdmin> listblog = articlemapper.selectAllArticleIndex();
		PageInfo<ArticleIndexAdmin> pageInfo = new PageInfo<ArticleIndexAdmin>(listblog);
		return pageInfo;
	}

	// 根據ID刪除文章
	@GetMapping("/admin/deleteArticle")
	public HashMap<String, Object> deleteArticle(Integer articleId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		blogService.deleteBlog(articleId);
		map.put("msg", "刪除成功!");
		return map;
	}

	// 发布文章
	@GetMapping("/admin/publishblog")
	public HashMap<String, Object> publishBlog(Integer articleId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int numback = articlemapper.publishByBlogId(articleId);
		map.put("msg", "发布成功!");
		return map;
	}

	// 前台查询个人博客类别列表索引
	@RequestMapping("/sortbloglist")
	public PageInfo<BlogIndex> getsortbloglist(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
		PageHelper.startPage(pageNum, 4);
		List<BlogIndex> listarticles = articlemapper.selectSortBlogIndex("个人博客");
		PageInfo<BlogIndex> pageInfo = new PageInfo<BlogIndex>(listarticles);
		return pageInfo;
	}

	// 前台查询技术心得类别列表索引
	@RequestMapping("/sortblogcardlist")
	public PageInfo<BlogIndex> getsortblogcardlist(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
		PageHelper.startPage(pageNum, 6);
		List<BlogIndex> listarticles = articlemapper.selectSortBlogIndex("技术心得");
		PageInfo<BlogIndex> pageInfo = new PageInfo<BlogIndex>(listarticles);
		return pageInfo;
	}

	// 前台首页查询前十最新文章
	@RequestMapping("/allbloglist")
	public List<BlogIndex> getallindex() {
		List<BlogIndex> listarticles = articlemapper.selectAllBlogIndex();
		return listarticles;
	}

	// 前台文章详情
	@RequestMapping("/blogdetail")
	public Articles getBlogDetail(Integer articleId) {
		Articles blog = articlemapper.selectByPrimaryKey(articleId);
		return blog;
	}

}
