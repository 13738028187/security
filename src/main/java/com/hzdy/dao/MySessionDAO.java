package com.hzdy.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzdy.entity.ShiroSession;
import com.hzdy.utils.SerializableUtils;


/**
 * <p>User: Zhang Jun
 * <p>Date: 17-9-5
 * <p>Version: 1.0
 */
public class MySessionDAO extends CachingSessionDAO {

    @Autowired
    private ShiroSessionDao shiroSessionDao;
    
    private static final Logger logger = Logger.getLogger(MySessionDAO.class);

    @Override
    protected Serializable doCreate(Session session) {
    	
        Serializable sessionId = generateSessionId(session);
        
        assignSessionId(session, sessionId);
        
        ShiroSession shiroSession = new ShiroSession();
        
        shiroSession.setCreateTime(new Date());
        
        shiroSession.setShiroSession(SerializableUtils.serialize(session));
        
        shiroSession.setShiroSessionId(sessionId.toString());
        
        logger.debug(Thread.currentThread().hashCode()+"thread name ="+Thread.currentThread().getName()+"====Serializable doCreate(Session session)======> sessionId.toString()>>>>>"+sessionId.toString());
        
        shiroSessionDao.insert(shiroSession);
        
        //String sql = "insert into hzdy_shiro_session(shiro_session_id, shiro_session,create_time) values(?,?,?)";
        
        //jdbcTemplate.update(sql, sessionId, SerializableUtils.serialize(session),new Date());
        
        return session.getId();
    }
    
    @Override
    protected void doUpdate(Session session) {
    	
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
           
        	return; //如果会话过期/停止 没必要再更新了
        }
        
        ShiroSession shiroSession = new ShiroSession();
        
        shiroSession.setCreateTime(new Date());
        
        shiroSession.setShiroSession(SerializableUtils.serialize(session));
        
        shiroSession.setShiroSessionId(session.getId().toString());
        
        logger.debug(Thread.currentThread().hashCode()+"thread name ="+Thread.currentThread().getName()+"===void doUpdate(Session session) =====> session.getId().toString() ===="+session.getId().toString());
        
        shiroSessionDao.updateByPrimaryKey(shiroSession);
        
        //String sql = "update hzdy_shiro_session set shiro_session=? ,create_time=? where shiro_session_id=?";
        
        //jdbcTemplate.update(sql, SerializableUtils.serialize(session), new Date(),session.getId());
    }
    
    @Override
    protected void doDelete(Session session) {
    	
    	logger.debug(Thread.currentThread().hashCode()+"thread name ="+Thread.currentThread().getName()+"===void doDelete(Session session)=====>session.getId().toString()=="+session.getId().toString());
    	
    	shiroSessionDao.deleteByPrimaryKey(session.getId().toString());
    	
        //String sql = "delete from hzdy_shiro_session where shiro_session_id=?";
        
        //jdbcTemplate.update(sql, session.getId());
    }
    
    @Override
    protected Session doReadSession(Serializable sessionId) {
    	
    	logger.debug(Thread.currentThread().hashCode()+"thread name ="+Thread.currentThread().getName()+"===Session doReadSession(Serializable sessionId)  =====> sessionId.toString()======="+sessionId.toString());
    	
    	List<String> sessionStrList = shiroSessionDao.findSessionByPrimaryKey(sessionId.toString());
        
        if(sessionStrList.size() == 0) {
        	return null;
        }
        
        return SerializableUtils.deserialize(sessionStrList.get(0));
    }
   

}
