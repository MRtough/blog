package com.blog.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.demo.dao.ArticleLabelMapper;
import com.blog.demo.dao.LabelsMapper;
import com.blog.demo.dao.SysUsersMapper;
import com.blog.demo.dao.UserRoleMapper;
import com.blog.demo.entity.ArticleLabel;
import com.blog.demo.entity.Labels;
import com.blog.demo.entity.SysUsers;
import com.blog.demo.service.LabelServiceImpl;
import com.blog.demo.service.UserServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class LabelController {
	
	@Autowired
	ArticleLabelMapper a_labelmapper;
	@Autowired
	LabelsMapper labelsmapper;
	@Autowired
	LabelServiceImpl labelService;
	
	@GetMapping("/getlabels")
	public PageInfo<Labels> getLabels(Integer pageNum) {
		// pageNum:表示第几页 pageSize:表示一页展示的数据
		PageHelper.startPage(pageNum, 10);
		List<Labels> listlabel = labelsmapper.selectAll();
		PageInfo<Labels> pageInfo = new PageInfo<Labels>(listlabel);
		return pageInfo;
	}

	@GetMapping("/admin/deletelable")
	public HashMap<String, Object> deleteLabel(Integer labelId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		labelService.deleteLabel(labelId);
		map.put("msg", "刪除成功!");
		return map;
	}

	// 根据文章ID添加标签
	@PostMapping("/admin/postlabel")
	public HashMap<String, Object> addbloglabel(@RequestParam List<String> labellist, @RequestParam Integer blogid) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ArticleLabel> list = new ArrayList<ArticleLabel>();
		for (int i = 0; i < labellist.size(); i++) {
			Labels label1 = labelsmapper.selectByLabelname(labellist.get(i));
			if (label1 == null) {
				Labels label2 = new Labels(labellist.get(i));
				labelsmapper.insert(label2);
				Integer labelid = label2.getLabelId();
				System.out.println("返回的标签ID：" + labelid);
				list.add(new ArticleLabel(blogid, labelid));
			} else {
				list.add(new ArticleLabel(blogid, label1.getLabelId()));
			}
		}
		a_labelmapper.addArtLabels(list);
		map.put("msg", "添加标签成功!");
		return map;
	}
	//前台文章详情获取对应标签
	@GetMapping("/getBlogLabel")
	public List<Labels> getBlogLabels(Integer blogId) {
		List<Labels> listlabel = labelsmapper.selectByBlogId(blogId);
		return listlabel;
	}
}
