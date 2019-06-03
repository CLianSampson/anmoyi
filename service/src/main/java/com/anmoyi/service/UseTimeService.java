package com.anmoyi.service;

import com.anmoyi.model.po.UseTime;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UseTimeService {

    void  addUseTime(UseTime useTime);

    /**
     * 获取某一天的使用次数
     * @param userId
     * @param useTime
     * @return
     */
    List<UseTime> getUseTimeList(int userId,  int pointType, Date useTime);


    /**
     * 获取一段时间的使用次数
     * @param userId
     * @param startTime
     * @param endTime
     * @param pointType  穴位类型
     * @return
     */
    List<Map<String,Object>> getPeriodUseTimeList(int userId,  int pointType ,Date startTime, Date endTime);

}
