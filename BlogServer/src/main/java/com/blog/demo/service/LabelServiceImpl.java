package com.blog.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.demo.dao.ArticleLabelMapper;
import com.blog.demo.dao.LabelsMapper;
@Service
public class LabelServiceImpl implements LabelService {
	@Autowired
	ArticleLabelMapper a_labelmapper;
	@Autowired
	LabelsMapper labelsmapper;
	@Override
	@Transactional
	public void deleteLabel(Integer labelId) {
		// TODO Auto-generated method stub
		int numback = labelsmapper.deleteByPrimaryKey(labelId);
		int numback1 = a_labelmapper.deleteByLabelId(labelId);
	}

}
