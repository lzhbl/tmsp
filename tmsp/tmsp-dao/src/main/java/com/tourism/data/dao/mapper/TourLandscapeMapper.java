package com.tourism.data.dao.mapper;

import com.tourism.data.dao.model.TourLandscape;
import com.tourism.data.dao.model.TourLandscapeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TourLandscapeMapper {
    long countByExample(TourLandscapeExample example);

    int deleteByExample(TourLandscapeExample example);

    int deleteByPrimaryKey(String id);

    int insert(TourLandscape record);

    int insertSelective(TourLandscape record);

    List<TourLandscape> selectByExample(TourLandscapeExample example);

    TourLandscape selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TourLandscape record, @Param("example") TourLandscapeExample example);

    int updateByExample(@Param("record") TourLandscape record, @Param("example") TourLandscapeExample example);

    int updateByPrimaryKeySelective(TourLandscape record);

    int updateByPrimaryKey(TourLandscape record);
}