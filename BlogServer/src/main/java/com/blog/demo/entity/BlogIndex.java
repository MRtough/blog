package com.blog.demo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
public class BlogIndex {
	private Integer articleId;
	private String articleTitle;
	private String articleContent;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date articleDate;
	private String articleCover;
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public Date getArticleDate() {
		return articleDate;
	}
	public void setArticleDate(Date articleDate) {
		this.articleDate = articleDate;
	}
	public String getArticleCover() {
		return articleCover;
	}
	public void setArticleCover(String articleCover) {
		this.articleCover = articleCover;
	}

}
