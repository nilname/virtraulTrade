package com.ooteco.service.impl;

import com.ooteco.mapper.InfomationMapper;
import com.ooteco.model.FirstHeadNewsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangqing on 3/1/18.
 */
@Service
public class InfoServiceHeadInfoDetail {
  @Autowired
    InfomationMapper infomationMapper;
 public List<FirstHeadNewsDetails> getHeadFirstByTitle(String title){
     return infomationMapper.getHeadFirstDetails(title);
  }
}
