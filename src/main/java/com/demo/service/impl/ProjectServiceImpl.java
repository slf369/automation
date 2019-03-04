package com.demo.service.impl;

import com.demo.entity.ProjectInfo;
import com.demo.repository.ProjectRepository;
import com.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shi.lingfeng on 2018/2/23.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository repository;

    @Override
    public ProjectInfo findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    public Page<ProjectInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<ProjectInfo> findAll() {
        return repository.findAll();
    }

    @Override
    public ProjectInfo save(ProjectInfo projectInfo) {
        return repository.save(projectInfo);
    }

    @Override
    public Page<ProjectInfo> findSearch(String search, Pageable pageable) {
        String str="%"+search+"%";
        return repository.findByProjectNameLike(str,pageable);
    }

    @Override
    public Page<ProjectInfo> findSearchById(Long id, Pageable pageable) {
        // TODO Auto-generated method stub
        return  repository.findById(id,pageable);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
