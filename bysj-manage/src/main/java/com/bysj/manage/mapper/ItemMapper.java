package com.bysj.manage.mapper;

import com.bysj.common.mapper.SysMapper;
import com.bysj.common.po.Item;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemMapper extends SysMapper<Item> {

    public List<Item> findItemByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    @Select("select name from tb_item_cat where id = #{itemId}")
    String findItemCatNameById(Long itemId);

    public void updateStatus(@Param("ids") String[] ids,@Param("status") int status);
}
