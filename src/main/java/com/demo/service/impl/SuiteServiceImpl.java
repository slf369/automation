package com.demo.service.impl;

import com.demo.entity.SuiteInfo;
import com.demo.repository.SuiteRepository;
import com.demo.service.SuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shi.lingfeng on 2018/2/2.
 */
@Service
public class SuiteServiceImpl implements SuiteService {

    @Autowired
    SuiteRepository suiteRepository;

    @Override
    public SuiteInfo findOne(Long id) {
        return suiteRepository.findOne(id);
    }

    @Override
    public Page<SuiteInfo> findAll(Pageable pageable) {
        return suiteRepository.findAll(pageable);
    }

    @Override
    public List<SuiteInfo> findAll() {
        return suiteRepository.findAll();
    }

    @Override
    public SuiteInfo save(SuiteInfo suiteInfo) {
        return suiteRepository.save(suiteInfo);
    }

    @Override
    public Page<SuiteInfo> findSearch(String search, Pageable pageable) {
        String str="%"+search+"%";
        return suiteRepository.findBySuiteNameLike(str,pageable);
    }

    @Override
    public void delete(Long id) {
        suiteRepository.delete(id);
    }

    @Override
    public Page<SuiteInfo> findSearchById(Long id, Pageable pageable) {
        // TODO Auto-generated method stub
        return  suiteRepository.findById(id,pageable);
    }

}
