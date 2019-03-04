package com.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.entity.ParamInfo;

public interface ParamRepository extends JpaRepository<ParamInfo, Long> {
	Page<ParamInfo> findByParamNameLike(String name,Pageable pageable);
	Page<ParamInfo> findById(Long id,Pageable pageable);
}
