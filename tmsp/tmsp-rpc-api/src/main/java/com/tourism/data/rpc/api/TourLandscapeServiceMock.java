package com.tourism.data.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.tourism.data.dao.mapper.TourLandscapeMapper;
import com.tourism.data.dao.model.TourLandscape;
import com.tourism.data.dao.model.TourLandscapeExample;

/**
* 降级实现TourLandscapeService接口
* Created by shuzheng on 2018/10/30.
*/
public class TourLandscapeServiceMock extends BaseServiceMock<TourLandscapeMapper, TourLandscape, TourLandscapeExample> implements TourLandscapeService {

}
