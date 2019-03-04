package com.demo.service;

import com.demo.entity.FuncInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by shi.lingfeng on 2018/3/30.
 */
public interface FuncService {

    FuncInfo findOne(Long id);
    Page<FuncInfo> findAll(Pageable pageable);
    List<FuncInfo> findAll();
    FuncInfo save(FuncInfo funcInfo);
    Page<FuncInfo> findSearch(String search, Pageable pageable);
    Page<FuncInfo> findSearchById(Long id, Pageable pageable);
    void delete(Long id);
}
