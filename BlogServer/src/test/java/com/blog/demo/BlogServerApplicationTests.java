package com.blog.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blog.demo.dao.ArticleLabelMapper;
import com.blog.demo.entity.ArticleLabel;

@SpringBootTest
class BlogServerApplicationTests {
	@Autowired 
	private static ArticleLabelMapper aLabelMapper;
	@Test
	void contextLoads() {
			List<ArticleLabel> list=new ArrayList<ArticleLabel>();
			list.add(new ArticleLabel(3, 1));
			list.add(new ArticleLabel(3, 2));
			aLabelMapper.addArtLabels(list);
		
	}

}
