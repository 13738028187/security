package com.hzdy.dao;

import java.util.List;

import com.hzdy.logger.entity.Email;

/**
 * @author zyd
 *
 */
public interface EmailDao extends BaseDao<Email> {
	List<Email> queryAll();
	void deleteEmailById(int emailId);
	int save(Email email);
	int update(Email email);
	List<Email> queryAllByUserId(int userId);
}