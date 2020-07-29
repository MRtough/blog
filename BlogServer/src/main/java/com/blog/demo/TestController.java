package com.blog.demo;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.demo.dao.ArticlesMapper;
import com.blog.demo.dao.CommentsMapper;
import com.blog.demo.dao.MessageMapper;
import com.blog.demo.entity.Articles;
import com.blog.demo.entity.BlogIndex;
import com.blog.demo.entity.Comments;
import com.blog.demo.entity.Message;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class TestController {
	@Autowired
	ArticlesMapper articlemapper;
	@Autowired
	CommentsMapper commentsmapper;
	@Autowired
	MessageMapper messagemapper;

	// 测试
	@RequestMapping("testtime")
	public String testinserttime() {
		Message test = new Message();
		test.setMessageUser("sdassdfsdda");
		test.setMessageContent("zxasdfsdada");
		Date data = new Date();
		test.setMessageData(data);
		messagemapper.insert(test);
		return "插入成功";
	}
	//测试
	@RequestMapping(value = "/getArticle")
	public PageInfo<Articles> getSomePerson(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
		// pageNum:表示第几页 pageSize:表示一页展示的数据
		PageHelper.startPage(pageNum, 3);
		List<Articles> listarticles = articlemapper.selectAll();
		PageInfo<Articles> pageInfo = new PageInfo<Articles>(listarticles);

		return pageInfo;
	}

}
