package com.bysj.common.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table(name = "tb_item") // 实体对象和表映射
@JsonIgnoreProperties(ignoreUnknown=true) // 表示json转化时忽略未知属性
public class Item extends BasePojo {

	@Id // 定义主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
	private Long id; // id
	private String title; // 标题
	// @Column(name="sell_point") //指定属性和字段一一映射 开启驼峰映射 省略此步
	private String sellPoint; // 卖点信息
	private Long price; // 商品价格 用long是因为，float精度问题 展现/10 数据库里面存*100 long计算快
	private Integer num; // 商品数量
	private String barcode; // 条形码 要用包装类型 基本类型有默认值0 和 null不一样
	private String image; // 图片地址 多个用逗号分开
	private Long cid; // 表示商品分类id
	private Integer status; // 状态信息 1.正常 2.下架 3.删除

	//为了满足页面需求  添加get方法
	public String[] getImages() {
		return image.split(",");
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
