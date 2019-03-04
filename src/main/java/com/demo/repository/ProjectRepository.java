package com.demo.repository;

import com.demo.entity.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Created by shi.lingfeng on 2018/3/23.
 */
public interface ProjectRepository extends JpaRepository<ProjectInfo,Long> {
    Page<ProjectInfo> findByProjectNameLike(String name, Pageable pageable);
    Page<ProjectInfo> findById(Long id,Pageable pageable);
}
