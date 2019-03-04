package com.demo.service.impl;

import com.demo.entity.FuncInfo;
import com.demo.repository.FuncRepository;
import com.demo.service.FuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shi.lingfeng on 2018/1/30.
 */
@Service
public class FuncServiceImpl implements FuncService {

    @Autowired
    FuncRepository funcRepository;

    @Override
    public FuncInfo findOne(Long id) {
        return funcRepository.findOne(id);
    }

    @Override
    public Page<FuncInfo> findAll(Pageable pageable) {
        return funcRepository.findAll(pageable);
    }

    @Override
    public List<FuncInfo> findAll() {
        return funcRepository.findAll();
    }

    @Override
    public FuncInfo save(FuncInfo funcInfo) {
        return funcRepository.save(funcInfo);
    }

    @Override
    public Page<FuncInfo> findSearch(String search, Pageable pageable) {
        String str="%"+search+"%";
        return funcRepository.findByFuncNameLike(str,pageable);
    }

    @Override
    public void delete(Long id) {
         funcRepository.delete(id);
    }

    @Override
    public Page<FuncInfo> findSearchById(Long id, Pageable pageable) {
        // TODO Auto-generated method stub
        return  funcRepository.findById(id,pageable);
    }

}
