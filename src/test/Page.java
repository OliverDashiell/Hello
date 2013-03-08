package test;

import java.util.ArrayList;
import java.util.Hashtable;

public class Page {
	private ArrayList list;
	private int count;
	private int offset;
	private int limit;
	private Hashtable criteria;
	
	public Page(ArrayList list, int count, int offset, int limit,
			Hashtable criteria) {
		this.list = list;
		this.count = count;
		this.offset = offset;
		this.limit = limit;
		this.criteria = criteria;
	}
	
	public void setList(ArrayList list) {
		this.list = list;
	}
	
	public ArrayList getList() {
		return list;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setCriteria(Hashtable criteria) {
		this.criteria = criteria;
	}
	
	public Hashtable getCriteria() {
		return criteria;
	}
}
