package com.anmoyi.service.impl;

import com.anmoyi.model.dao.UseTimeMapper;
import com.anmoyi.model.po.UseTime;
import com.anmoyi.service.UseTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public List<UseTime> getUseTimeList(int userId, int pointType, Date useTime) {
        return useTimeMapper.getUseTimeList(userId, pointType, useTime);
    }


    @Override
    public List<Map<String,Object>> getPeriodUseTimeList(int userId, int pointType ,Date startTime, Date endTime) {
        return useTimeMapper.getPeriodUseTimeList(userId, pointType, startTime, endTime);

    }



}

