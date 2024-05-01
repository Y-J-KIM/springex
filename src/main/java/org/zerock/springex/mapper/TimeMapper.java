package org.zerock.springex.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {

    //셀렉트문 select now()로 DB 에서 날짜를 가져옴
    @Select("select now()")
    String getTime();
}
