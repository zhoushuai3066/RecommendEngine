package com.busap.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class RecommendInfo {

	
	@Id
	private long userId;
	
	@Field("commonFriends")
	private long[] commonFriends;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long[] getCommonFriends() {
		return commonFriends;
	}

	public void setCommonFriends(long[] commonFriends) {
		this.commonFriends = commonFriends;
	}
	
	
	
}
