package com.ooteco.service.impl;

import com.ooteco.page.PageBean;
import com.ooteco.service.InfoService;

import java.util.List;

public class BaseInfoService implements InfoService {
    @Override
    public PageBean<?> getInfo(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<?> getInfoBySource(String source) {
        return null;
    }

    @Override
    public List<?> getInfoByDate(String date) {
        return null;
    }

    @Override
    public List<?> getInfoAll() {
        return null;
    }
}
