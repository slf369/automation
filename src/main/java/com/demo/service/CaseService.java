package com.demo.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.demo.entity.CaseInfo;


public interface CaseService {
	CaseInfo findOne(Long id);
    Page<CaseInfo> findAll(Pageable pageable);
    List<CaseInfo> findAll();
    CaseInfo save(CaseInfo id);
    Page<CaseInfo> findSearch(String search, Pageable pageable);
    Page<CaseInfo> findSearchById(Long id, Pageable pageable);
    void delete(Long id);
}
