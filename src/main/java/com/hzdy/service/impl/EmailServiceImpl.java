package com.hzdy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.dao.EmailDao;
import com.hzdy.logger.entity.Email;
import com.hzdy.logger.entity.EmailContent;
import com.hzdy.logger.utils.MailUtils;
import com.hzdy.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Resource
	private EmailDao emailDao;

	@Override
	public int queryTotal() {
		return emailDao.queryTotal();
	}

	@Override
	public Email queryObject(Integer id) {
		return emailDao.queryObject(id);
	}

	@Override
	public List<Email> queryAll() {
		return emailDao.queryAll();
	}

	@Override
	public void save(Email email) {
		emailDao.save(email);
	}

	@Override
	public void update(Email email) {
		emailDao.update(email);
	}

	@Override
	public void deleteEmailById(Integer emailId) {
		emailDao.deleteEmailById(emailId);
	}

	@Override
	public Boolean sendEmail(EmailContent emailContent) {
		List<Email> sendEmailList=queryAll();
		return MailUtils.getInstance().sendMail(sendEmailList, emailContent);
	}

	@Override
	public List<Email> queryAllByUserId(int userId) {
		// TODO Auto-generated method stub
		return emailDao.queryAllByUserId(userId);
	}
}
