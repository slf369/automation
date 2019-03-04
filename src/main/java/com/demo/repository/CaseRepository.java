package com.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.entity.CaseInfo;


public interface CaseRepository extends JpaRepository<CaseInfo, Long>{
	Page<CaseInfo> findByCaseNameLike(String name,Pageable pageable);
	Page<CaseInfo> findById(Long id,Pageable pageable);
}
