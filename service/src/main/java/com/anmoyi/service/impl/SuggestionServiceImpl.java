package com.anmoyi.service.impl;

import com.anmoyi.model.dao.SuggestionMapper;
import com.anmoyi.model.po.Suggestion;
import com.anmoyi.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class SuggestionServiceImpl implements SuggestionService{

    @Autowired
    private SuggestionMapper suggestionMapper;


    @Override
    public void addSuggestion(Suggestion suggestion) {
        suggestion.setCreateTime(new Date());

        suggestionMapper.insertSelective(suggestion);
    }


}
