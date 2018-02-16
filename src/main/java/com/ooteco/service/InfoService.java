package com.ooteco.service;

import com.ooteco.page.PageBean;

import java.util.List;

/**
 * Created by fangqing on 2/10/18.
 */
public interface InfoService {
    public PageBean<?> getInfo(int pageNum,int pageSize);
    public List<?> getInfoAll();
    public List<?> getInfoBySource(String source);
    public List<?> getInfoByDate(String date);
}
