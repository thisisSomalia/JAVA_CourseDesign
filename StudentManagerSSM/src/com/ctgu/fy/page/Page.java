package com.ctgu.fy.page;

import org.springframework.stereotype.Component;
/**
 * 分页类封装
 * @author fy
 *
 */
@Component
public class Page {
	private int page;//当前页面
	
	private int rows;//每页显示数量
	
	private int offset;//起始页数，总0开始

	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int row) {
		this.rows = row;
	}

	public int getOffset() {
		this.offset = (page -1)*rows;
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = (page -1)*rows;
	}
	
	
}
