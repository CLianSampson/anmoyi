package com.anmoyi.service.impl;

import com.anmoyi.model.dao.UseTimeMapper;
import com.anmoyi.model.po.UseTime;
import com.anmoyi.service.UseTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UseTimeServiceImpl  implements UseTimeService{


    @Autowired
    private UseTimeMapper useTimeMapper;

    @Transactional
    @Override
    public void addUseTime(UseTime useTime) {
        useTimeMapper.insertSelective(useTime);
    }


    @Override
    public List<UseTime> getUseTimeList(int userId, Date useTime) {
        return useTimeMapper.getUseTimeList(userId,useTime);
    }
}
