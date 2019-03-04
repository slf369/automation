package com.demo.service;

import com.demo.entity.ExtractInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by shi.lingfeng on 2018/3/28 0028.
 */
public interface ExtractService {
    ExtractInfo findOne(Long id);
    Page<ExtractInfo> findAll(Pageable pageable);
    List<ExtractInfo> findAll();
    ExtractInfo save(ExtractInfo entiy);
    Page<ExtractInfo> findSearch(String search, Pageable pageable);
    Page<ExtractInfo> findSearchById(Long id, Pageable pageable);
    void delete(Long id);
}
