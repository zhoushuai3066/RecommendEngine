package com.busap.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.busap.bean.User_Relationship_Recommend;


public class  ISimilarity implements Runnable{

	
	private UserNeighborhood neighborhood;
	private long uid;
	private DataModel dataModel;
	private MongoOperations mongoOperations;
    List<Long> resultlist = null;
    
    private LongPrimitiveIterator userIds;
    private List<LongPrimitiveIterator> listUserIds;
    private int startIndex;	//�߳̿�ʼλ��
    private int endIndex;	//�߳̽���λ��
    
    private int listId;		//��ŵ�userId�����ַ
    //�Ƽ����ƺ�����������������������ȡ����к�����
	private static final int count = 10;
	public ISimilarity (DataModel dataModel,UserNeighborhood userNeighborhood ,MongoOperations mongoOperations,List<LongPrimitiveIterator> listUserIds,int startIndex,int endIndex,int listId){
		this.dataModel 			= dataModel;
		this.neighborhood 		= userNeighborhood;
		this.mongoOperations 	= mongoOperations;
		this.listUserIds 		= listUserIds;
		this.startIndex 		= startIndex;
		this.endIndex			= endIndex;
		this.listId				= listId;
	}
	
	public void run() {
		userIds = listUserIds.get(listId);
		userIds.skip(startIndex);
		System.out.println("uid = " + uid +"*** CurrentThread = " + Thread.currentThread().getName() +"&&&&&startIndex = "+ startIndex);
		for (int i = startIndex; i <= endIndex; i++) {
			//System.out.println("statId" +  startIndex +"88888888" + Thread.currentThread().getName());
			while(userIds.hasNext()){
				try {
					 uid = userIds.next();
					 System.out.println("uid = " + uid);
					long[] similarUsers = neighborhood.getUserNeighborhood(uid);
					FastIDSet fsids = dataModel.getItemIDsFromUser(uid);
					int initcount = 0;
					//�ų��Ѿ��Ǻ��ѹ�ϵ�������û������ҳ�10�������Ƶĺ���
					resultlist = new ArrayList<Long>();
					for(long similarUser:similarUsers){
						//�Ƿ��Ѿ��Ǻ���
						if(!fsids.contains(similarUser)){
							if(initcount>=count){
								break;
							}else{
								resultlist.add(similarUser);
								initcount++;
							}
						}
					}
					//insertOrUpdate���ƺ���
					mongoOperations.upsert(new Query(Criteria.where("userId").is(uid)), new Update().update("recomeneds", resultlist), User_Relationship_Recommend.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
   }
}
