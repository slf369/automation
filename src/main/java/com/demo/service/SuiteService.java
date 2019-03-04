package com.demo.service;

import com.demo.entity.SuiteInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by shi.lingfeng on 2018/3/2.
 */
public interface SuiteService {
    SuiteInfo findOne(Long id);
    Page<SuiteInfo> findAll(Pageable pageable);
    List<SuiteInfo> findAll();
    SuiteInfo save(SuiteInfo suiteInfo);
    Page<SuiteInfo> findSearch(String search, Pageable pageable);
    Page<SuiteInfo> findSearchById(Long id, Pageable pageable);
    void delete(Long id);
}
