package com.anmoyi.model.dao;

import com.anmoyi.model.po.UseTime;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UseTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UseTime record);

    int insertSelective(UseTime record);

    UseTime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UseTime record);

    int updateByPrimaryKey(UseTime record);


    List<UseTime> getUseTimeList(@Param("userId") int userId, @Param("pointType") int pointType ,@Param("useTime") Date useTime);


    List<Map<String,Object>> getPeriodUseTimeList(@Param("userId") int userId, @Param("pointType") int pointType ,@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}