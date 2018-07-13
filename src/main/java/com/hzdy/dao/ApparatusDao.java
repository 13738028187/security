package com.hzdy.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hzdy.entity.Apparatus;
import com.hzdy.entity.BaselineStrategyDevice;
import com.hzdy.logger.entity.Email;

public interface ApparatusDao extends BaseDao<Apparatus> {

	ArrayList<Apparatus> queryAll();
	
}
