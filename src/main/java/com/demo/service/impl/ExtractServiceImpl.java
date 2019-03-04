package com.demo.service.impl;

import com.demo.entity.ExtractInfo;
import com.demo.repository.ExtractRepository;
import com.demo.service.ExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/1/28 0028.
 */

@Service
public class ExtractServiceImpl implements ExtractService {

    @Autowired
    ExtractRepository extractRepository;

    @Override
    public ExtractInfo findOne(Long id) {
        return extractRepository.findOne(id);
    }

    @Override
    public Page<ExtractInfo> findAll(Pageable pageable) {
        return extractRepository.findAll(pageable);
    }

    @Override
    public List<ExtractInfo> findAll() {
        return extractRepository.findAll();
    }

    @Override
    public ExtractInfo save(ExtractInfo entity) {
        return extractRepository.save(entity);
    }

    @Override
    public Page<ExtractInfo> findSearch(String search, Pageable pageable) {
        String str="%"+search+"%";
        return extractRepository.findByExtractNameLike(str,pageable);
    }

    @Override
    public void delete(Long id) {
        extractRepository.delete(id);
    }

    @Override
    public Page<ExtractInfo> findSearchById(Long id, Pageable pageable) {
        // TODO Auto-generated method stub
        return  extractRepository.findById(id,pageable);
    }

}
