package com.blog.demo.dao;

import com.blog.demo.entity.Message;
import java.util.List;

public interface MessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated Sun Jun 28 15:08:43 GMT+08:00 2020
     */
    int deleteByPrimaryKey(Integer messageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated Sun Jun 28 15:08:43 GMT+08:00 2020
     */
    int insert(Message record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated Sun Jun 28 15:08:43 GMT+08:00 2020
     */
    Message selectByPrimaryKey(Integer messageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated Sun Jun 28 15:08:43 GMT+08:00 2020
     */
    List<Message> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message
     *
     * @mbg.generated Sun Jun 28 15:08:43 GMT+08:00 2020
     */
    int updateByPrimaryKey(Message record);
}