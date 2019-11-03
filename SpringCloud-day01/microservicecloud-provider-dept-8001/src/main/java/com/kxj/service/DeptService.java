package com.kxj.service;

import com.kxj.entity.Dept;

import java.util.List;


public interface DeptService
{
	public boolean add(Dept dept);

	public Dept get(Long id);

	public List<Dept> list();
}
