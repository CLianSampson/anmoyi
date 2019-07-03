package com.anmoyi.service.impl;

import com.anmoyi.common.DateUtil;
import com.anmoyi.model.dao.UseTimeMapper;
import com.anmoyi.model.po.UseTime;
import com.anmoyi.service.UseTimeService;
import com.anmoyi.service.vo.UseTimeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public List<UseTime> getUseTimeList(int userId, int pointType, String useTime) {
        return useTimeMapper.getUseTimeList(userId, pointType, useTime);
    }


    @Override
    public List<UseTimeVO> getPeriodUseTimeList(int userId, int pointType ,Date startTime, Date endTime) {
        List<Map<String,Object>>  list = useTimeMapper.getPeriodUseTimeList(userId, pointType, startTime, endTime);

        if (null == list){
            return null;
        }


        List<UseTimeVO> returnList = new ArrayList<>();

        for (Map<String,Object> map: list) {

            String currentDayStr = (String) map.get("time");

            List<UseTime> useTimes = useTimeMapper.getUseTimeList(userId, pointType, currentDayStr);

            UseTimeVO useTimeVO = new UseTimeVO();
            useTimeVO.setMap(map);
            useTimeVO.setList(useTimes);

            returnList.add(useTimeVO);

        }

        return returnList;
    }



}

