package com.blog.demo.dao;

import com.blog.demo.entity.ArticleIndexAdmin;
import com.blog.demo.entity.Articles;
import com.blog.demo.entity.BlogIndex;

import java.util.List;

public interface ArticlesMapper {
	//按分类查询博客索引
		List<BlogIndex> selectSortBlogIndex(String sort_name);
	//查询前十条博客索引
		List<BlogIndex> selectAllBlogIndex();
		//後台系統 查詢文章索引
		List<ArticleIndexAdmin> selectAllArticleIndex();
		//將文章狀態更新為發佈
		int publishByBlogId(Integer articleId);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbg.generated Sat Jun 27 15:50:42 CST 2020
     */
    int deleteByPrimaryKey(Integer articleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbg.generated Sat Jun 27 15:50:42 CST 2020
     */
    int insert(Articles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbg.generated Sat Jun 27 15:50:42 CST 2020
     */
    Articles selectByPrimaryKey(Integer articleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbg.generated Sat Jun 27 15:50:42 CST 2020
     */
    List<Articles> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbg.generated Sat Jun 27 15:50:42 CST 2020
     */
    int updateByPrimaryKey(Articles record);
}