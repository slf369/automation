package com.demo.repository;

import com.demo.entity.FuncInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shi.lingfeng on 2018/3/30.
 */
public interface FuncRepository extends JpaRepository<FuncInfo,Long> {
    Page<FuncInfo> findByFuncNameLike(String name, Pageable pageable);
    Page<FuncInfo> findById(Long id,Pageable pageable);
}
