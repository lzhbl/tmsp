package com.tourism.data.rpc.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImpl;
import com.tourism.data.dao.mapper.TourLandscapeMapper;
import com.tourism.data.dao.model.TourLandscape;
import com.tourism.data.dao.model.TourLandscapeExample;
import com.tourism.data.rpc.api.TourLandscapeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* TourLandscapeService实现
* Created by shuzheng on 2018/10/30.
*/
@Service
@Transactional
@BaseService
public class TourLandscapeServiceImpl extends BaseServiceImpl<TourLandscapeMapper, TourLandscape, TourLandscapeExample> implements TourLandscapeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TourLandscapeServiceImpl.class);

    @Autowired
    TourLandscapeMapper tourLandscapeMapper;

}