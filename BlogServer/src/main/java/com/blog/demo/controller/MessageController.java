package com.blog.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.demo.dao.MessageMapper;
import com.blog.demo.entity.Message;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class MessageController {
	@Autowired
	MessageMapper messagemapper;

	// 分页查询留言
	@RequestMapping(value = "/getallmessage", method = RequestMethod.GET)
	public PageInfo<Message> getAllMessage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<Message> listmessage = messagemapper.selectAll();
		PageInfo<Message> pageInfo = new PageInfo<Message>(listmessage);
		return pageInfo;
	}

	// 根據ID刪除留言
	@GetMapping("/admin/deleteMsg")
	public HashMap<String, Object> deleteMsg(Integer messageId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int numback = messagemapper.deleteByPrimaryKey(messageId);
		map.put("msg", "刪除成功!");
		return map;
	}

	// 前台插入一条留言
	@PostMapping("/postmessage")
	public String setmessage(@RequestBody Message message) {
		Date data = new Date();
		message.setMessageData(data);
		int backnum = messagemapper.insert(message);
		return "留言成功！";
	}

	// 前台分页查询留言
	@RequestMapping(value = "/allmessage", method = RequestMethod.GET)
	public PageInfo<Message> getMessage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		List<Message> listmessage = messagemapper.selectAll();
		PageInfo<Message> pageInfo = new PageInfo<Message>(listmessage);
		return pageInfo;
	}
}
