package com.bysj.common.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name="tb_webindex")
public class WebIndex implements Serializable {

    @Id // 定义主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id; // id

    private Integer block;

    private Integer col;

    private Long itemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "Index{" +
                "id=" + id +
                ", block=" + block +
                ", col=" + col +
                ", itemId=" + itemId +
                '}';
    }
}