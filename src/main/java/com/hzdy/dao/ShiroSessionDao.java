package com.hzdy.dao;

import java.util.List;

import com.hzdy.entity.ShiroSession;

public interface ShiroSessionDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzdy_shiro_session
     *
     * @mbggenerated Fri Feb 10 15:43:02 CST 2017
     */
    int deleteByPrimaryKey(String shiroSessionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzdy_shiro_session
     *
     * @mbggenerated Fri Feb 10 15:43:02 CST 2017
     */
    int insert(ShiroSession record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzdy_shiro_session
     *
     * @mbggenerated Fri Feb 10 15:43:02 CST 2017
     */
    int insertSelective(ShiroSession record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzdy_shiro_session
     *
     * @mbggenerated Fri Feb 10 15:43:02 CST 2017
     */
    ShiroSession selectByPrimaryKey(String shiroSessionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzdy_shiro_session
     *
     * @mbggenerated Fri Feb 10 15:43:02 CST 2017
     */
    int updateByPrimaryKeySelective(ShiroSession record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzdy_shiro_session
     *
     * @mbggenerated Fri Feb 10 15:43:02 CST 2017
     */
    int updateByPrimaryKey(ShiroSession record);
    
    
    List<String> findSessionByPrimaryKey(String shiroSessionId);
    
    /**
     * 删除(24-48)小时前的失效数据
     * 
     * @return 删除的行数
     */
    int deleteOldData();
}