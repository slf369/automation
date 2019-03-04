package com.demo.repository;

import com.demo.entity.ExtractInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shi.lingfeng on 2018/3/28 0028.
 */
public interface ExtractRepository extends JpaRepository<ExtractInfo,Long> {
    Page<ExtractInfo> findByExtractNameLike(String name, Pageable pageable);
    Page<ExtractInfo> findById(Long id,Pageable pageable);
}
