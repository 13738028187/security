package com.hzdy.service;

import java.util.List;

import com.hzdy.logger.entity.Email;
import com.hzdy.logger.entity.EmailContent;

public interface EmailService {

	int queryTotal();

	Email queryObject(Integer id);
	
	List<Email> queryAll();
	
	List<Email> queryAllByUserId(int userId);
	
	void save(Email email);
	
	void update(Email email);

	void deleteEmailById(Integer emailId);
	
	Boolean sendEmail(EmailContent emailContent);
}
