package com.demo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.demo.entity.CaseInfo;
import com.demo.repository.CaseRepository;
import com.demo.service.CaseService;


@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	private CaseRepository caseRepository;
	
	@Override
	public CaseInfo findOne(Long id) {		
		return caseRepository.findOne(id);
	}

	@Override
	public Page<CaseInfo> findAll(Pageable pageable) {		
		return caseRepository.findAll(pageable);
	}

	@Override
	public CaseInfo save(CaseInfo caseInfo) {		
		return caseRepository.save(caseInfo);
	}

	@Override
	public List<CaseInfo> findAll() {
		
		return caseRepository.findAll();
	}

	@Override
	public Page<CaseInfo> findSearch(String search, Pageable pageable) {
		// TODO Auto-generated method stub
		String str="%"+search+"%";
		return caseRepository.findByCaseNameLike(str, pageable);
	}

	@Override
	public void delete(Long id){
		caseRepository.delete(id);
	}

	@Override
	public Page<CaseInfo> findSearchById(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		 return  caseRepository.findById(id,pageable);
	}


}
