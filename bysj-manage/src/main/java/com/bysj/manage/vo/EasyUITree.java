package com.bysj.manage.vo;

public class EasyUITree {
	/**
	 * id text节点名称 state状态poen/close
	 */
	private Long id; // 商品分类id号
	private String text; // 商品分类名称
	private String state; // 状态
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public EasyUITree() {
		super();
		// 不加无参构造  反射和创建对象是不方便
	}
	public EasyUITree(Long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}
	@Override
	public String toString() {
		return "EasyUITree [id=" + id + ", text=" + text + ", state=" + state + "]";
	}
	
}
