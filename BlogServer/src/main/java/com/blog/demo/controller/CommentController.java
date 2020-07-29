package com.blog.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.demo.dao.CommentsMapper;
import com.blog.demo.entity.Comments;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class CommentController {
	@Autowired
	CommentsMapper commentsmapper;
	// 根據文章ID查詢留言
	@GetMapping("/allComments")
	public HashMap<String, Object> blogComments(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			Integer articleId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		PageHelper.startPage(pageNum, 5);
		List<Comments> listComments = commentsmapper.selectByArticleId(articleId);
		PageInfo<Comments> pageInfo = new PageInfo<Comments>(listComments);
		map.put("pageInfo", pageInfo);
		map.put("msg", "查詢成功!");
		return map;
	}
	// 根據留言ID删除留言
	@GetMapping("/admin/deleteComment")
	public HashMap<String, Object> deleteComment(Integer commentId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int numback = commentsmapper.deleteByPrimaryKey(commentId);
		map.put("msg", "删除成功!");
		return map;
	}
	// 前台根据文章id查询评论列表
	@RequestMapping("/blogcomments")
	public PageInfo<Comments> getComments(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			Integer articleId) {
		PageHelper.startPage(pageNum, 6);
		List<Comments> listcomment = commentsmapper.selectByArticleId(articleId);
		PageInfo<Comments> pageInfo = new PageInfo<Comments>(listcomment);
		return pageInfo;
	}
	// 前台为当前文章插入评论
	@PostMapping("/postcomment")
	public String setmessage(@RequestBody Comments comments) {
		Date data = new Date();
		comments.setCommentDate(data);
		int backnum = commentsmapper.insert(comments);
		return "评论成功！";
	}
}
