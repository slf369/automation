package com.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.demo.entity.ParamInfo;

public interface ParamService {
	ParamInfo findOne(Long id);
    Page<ParamInfo> findAll(Pageable pageable);
    List<ParamInfo> findAll();
    ParamInfo save(ParamInfo id);
    Page<ParamInfo> findSearch(String search, Pageable pageable);
    Page<ParamInfo> findSearchById(Long id, Pageable pageable);
    void delete(Long id);
}
