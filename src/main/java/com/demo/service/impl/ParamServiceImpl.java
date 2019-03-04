package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.entity.ParamInfo;
import com.demo.repository.ParamRepository;
import com.demo.service.ParamService;

@Service
public class ParamServiceImpl implements ParamService {

	@Autowired
	ParamRepository paramRepository;
	
	@Override
	public ParamInfo findOne(Long id) {
		// TODO Auto-generated method stub
		return paramRepository.findOne(id);
	}

	@Override
	public Page<ParamInfo> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return paramRepository.findAll(pageable) ;
	}

	@Override
	public List<ParamInfo> findAll() {
		// TODO Auto-generated method stub
		return paramRepository.findAll();
	}

	@Override
	public ParamInfo save(ParamInfo entity) {
		// TODO Auto-generated method stub
		return paramRepository.save(entity);
	}

	@Override
	public Page<ParamInfo> findSearch(String search, Pageable pageable) {
		// TODO Auto-generated method stub
		String str="%"+search+"%";
		return paramRepository.findByParamNameLike(str, pageable);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		paramRepository.delete(id);
	}

	@Override
	public Page<ParamInfo> findSearchById(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return  paramRepository.findById(id,pageable);
	}

}
