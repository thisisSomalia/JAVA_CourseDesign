package com.ctgu.fy.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ctgu.fy.entity.Clazz;
import com.ctgu.fy.entity.Grade;

/**
 * °à¼¶service
 * @author fy
 *
 */
@Service
public interface ClazzService {
	public int add(Clazz clazz);
	public int edit(Clazz clazz);
	public int delete(String ids);
	public List<Clazz> findList(Map<String,Object> queryMap);
	public List<Clazz> findAll();
	public int getTotal(Map<String,Object> queryMap);
}
