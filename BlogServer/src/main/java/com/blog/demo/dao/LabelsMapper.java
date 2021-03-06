package com.blog.demo.dao;

import com.blog.demo.entity.Labels;
import java.util.List;

public interface LabelsMapper {
	//根据标签名查询
	Labels selectByLabelname(String labelname);
	//
	List<Labels> selectByBlogId(Integer articleId);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labels
     *
     * @mbg.generated Fri Jul 17 10:23:53 CST 2020
     */
    int deleteByPrimaryKey(Integer labelId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labels
     *
     * @mbg.generated Fri Jul 17 10:23:53 CST 2020
     */
    int insert(Labels record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labels
     *
     * @mbg.generated Fri Jul 17 10:23:53 CST 2020
     */
    Labels selectByPrimaryKey(Integer labelId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labels
     *
     * @mbg.generated Fri Jul 17 10:23:53 CST 2020
     */
    List<Labels> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labels
     *
     * @mbg.generated Fri Jul 17 10:23:53 CST 2020
     */
    int updateByPrimaryKey(Labels record);
}