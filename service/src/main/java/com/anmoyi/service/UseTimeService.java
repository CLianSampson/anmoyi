package com.anmoyi.service;

import com.anmoyi.model.po.UseTime;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface UseTimeService {

    void  addUseTime(UseTime useTime);

    List<UseTime> getUseTimeList(int userId, Date useTime);

}
