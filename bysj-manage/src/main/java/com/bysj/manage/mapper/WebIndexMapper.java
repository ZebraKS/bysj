package com.bysj.manage.mapper;

import com.bysj.common.mapper.SysMapper;
import com.bysj.common.po.WebIndex;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WebIndexMapper extends SysMapper<WebIndex> {

    @Select("select * from tb_webindex order by id")
    List<WebIndex> findAll();
}
