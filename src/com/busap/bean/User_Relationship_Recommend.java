package com.busap.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class User_Relationship_Recommend {
	
	@Id
	private long userId;
	
	
	@Field("recomeneds")
	private RecommendInfo[] recomeneds;


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	

	
	
	public RecommendInfo[] getRecomeneds() {
		return recomeneds;
	}


	public void setRecomeneds(RecommendInfo[] recomeneds) {
		this.recomeneds = recomeneds;
	}


	public static void main(String[] args){

		List a = new ArrayList();
		List b = new ArrayList();
		
		a.add(1);
		a.add(2);
		a.add(3);
		
		b.add(2);
		b.add(3);
		b.add(4);
		a.retainAll(b);
		System.out.println(a);
		
		
	}
}
