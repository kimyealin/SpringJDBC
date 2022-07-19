package yeling.spring.guestbook.vo;

import java.util.List;

public class GuestMessageList {
	private int totalCount;
	private int pageNum;
	private int begin;
	private int end;
	private List<GuestMessage> messages;
	
	public GuestMessageList(int totalCount, int pageNum, int begin, int end, List<GuestMessage> messages) {
		this.totalCount = totalCount;
		this.pageNum = pageNum;
		this.begin = begin;
		this.end = end;
		this.messages = messages;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public List<GuestMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<GuestMessage> messages) {
		this.messages = messages;
	}
}
