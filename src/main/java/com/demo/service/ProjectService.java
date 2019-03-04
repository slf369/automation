package com.demo.service;

import com.demo.entity.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by shi.lingfeng on 2018/3/23.
 */
public interface ProjectService {
    ProjectInfo findOne(Long id);
    Page<ProjectInfo> findAll(Pageable pageable);
    List<ProjectInfo> findAll();
    ProjectInfo save(ProjectInfo suiteInfo);
    Page<ProjectInfo> findSearch(String search, Pageable pageable);
    Page<ProjectInfo> findSearchById(Long id, Pageable pageable);
    void delete(Long id);
}
