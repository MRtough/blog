package com.blog.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.blog.demo.dao.ArticleLabelMapper;
import com.blog.demo.dao.ArticlesMapper;
import com.blog.demo.dao.CommentsMapper;
import com.blog.demo.entity.Articles;

import net.coobird.thumbnailator.Thumbnails;
@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	ArticlesMapper articlesMapper;
	@Autowired
	CommentsMapper commentsmapper;
	@Autowired
	ArticleLabelMapper artlabmapper;
	@Override
	public List<String> uploadImgInBlog(MultipartFile[] _img) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		// 判断file数组不能为空并且长度大于0
		if (_img != null) {
			System.out.println("文件不为空");
			// 文件上传到的保存位置
			File path = new File("src/main/resources/");
			File upload = new File(path.getAbsolutePath(), "static/uploadFile/blogImg/");
			if (!upload.exists()) {
				upload.mkdirs();
			}
			for (int i = 0; i < _img.length; i++) {
				MultipartFile file = _img[i];
				// 文件名
				String filename = UUID.randomUUID().toString() + "."
						+ FilenameUtils.getExtension(file.getOriginalFilename());
				try {
					file.transferTo(new File(upload, filename));
					list.add(filename);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public Integer addBlog(Articles blog,MultipartFile imgfile) {
		// TODO Auto-generated method stub
		if (imgfile != null) {
			System.out.println("不为空");
			// 文件上传到的保存位置
			File path = new File("src/main/resources/");
			File upload = new File(path.getAbsolutePath(), "static/uploadFile/blogCover/");
			if (!upload.exists()) {
				upload.mkdirs();
			}
			String filename = UUID.randomUUID().toString() + "."
					+ FilenameUtils.getExtension(imgfile.getOriginalFilename());

			System.out.println(filename);

			try {
				// 图片处理开源框架Thumbnailator
				Thumbnails.of(imgfile.getInputStream()).size(500, 330).keepAspectRatio(false)
						.toFile(new File(upload, filename));
				// imgfile.transferTo(new File(realPath, filename));
				blog.setArticleCover(filename);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int numback=articlesMapper.insert(blog);
		return blog.getArticleId();
	}

	@Override
	@Transactional
	public void deleteBlog(Integer blogId) {
		// TODO Auto-generated method stub
		int numback = articlesMapper.deleteByPrimaryKey(blogId);
		int numback1 = commentsmapper.deleteByarticleId(blogId);
		int numback2 = artlabmapper.deleteByBlogId(blogId);
	}

	
}
