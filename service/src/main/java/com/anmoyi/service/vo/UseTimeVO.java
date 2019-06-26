package com.anmoyi.service.vo;

import com.anmoyi.model.po.UseTime;

import java.util.List;
import java.util.Map;

public class UseTimeVO {

    private  Map<String,Object> map;


    private List<UseTime> list;


    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<UseTime> getList() {
        return list;
    }

    public void setList(List<UseTime> list) {
        this.list = list;
    }
}
