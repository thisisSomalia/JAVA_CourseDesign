package com.ctgu.fy.page;

import org.springframework.stereotype.Component;
/**
 * ��ҳ���װ
 * @author fy
 *
 */
@Component
public class Page {
	private int page;//��ǰҳ��
	
	private int rows;//ÿҳ��ʾ����
	
	private int offset;//��ʼҳ������0��ʼ

	

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
