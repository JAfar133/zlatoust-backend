package com.zlatoust.mapper.contents;

import com.zlatoust.models.News;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewsMapper extends ContentMapper<News> {
}
