package com.demo.repository;

import com.demo.entity.SuiteInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shi.lingfeng on 2018/3/2.
 */
public interface SuiteRepository  extends JpaRepository<SuiteInfo,Long>{
    Page<SuiteInfo> findBySuiteNameLike(String name, Pageable pageable);
    Page<SuiteInfo> findById(Long id,Pageable pageable);
}
