package com.ctgu.fy.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ctgu.fy.entity.Student;

/**
 * ѧ��service
 * @author fy
 *
 */
@Service
public interface StudentService {
	public Student findByUserName(String username);
	public int add(Student student);
	public int edit(Student student);
	public int delete(String ids);
	public List<Student> findList(Map<String,Object> queryMap);
	public List<Student> findAll();
	public int getTotal(Map<String,Object> queryMap);
}
