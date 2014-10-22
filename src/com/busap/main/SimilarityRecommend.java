package com.busap.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.busap.bean.RecommendInfo;
import com.busap.bean.User_Relationship_Recommend;
import com.busap.conf.SpringMongoConfig;

public class SimilarityRecommend {

	
	private static ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	private static MongoOperations mongoOperations = (MongoOperations) ctx.getBean(MongoOperations.class);
	
	private static int count = 10;
	
	
	public static void main(String[] args) throws IOException,TasteException{
		   String dataPath = args[0];
		   String recommendedPath = args[1];
//		    String path  = "G:/mahoutworkspace/zhoushuai/data/tanimotoCoefficientSimilarityData.txt";
		   File dataFile = new File(dataPath);
		   File recommendedFile = new File(recommendedPath);
		   DataModel dataModel = new FileDataModel(dataFile);
		   DataModel recommendModel = new FileDataModel(recommendedFile); 
	       UserSimilarity userSimilarity = new TanimotoCoefficientSimilarity(dataModel);  
	       UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, userSimilarity, dataModel);  
	       LongPrimitiveIterator userIds = dataModel.getUserIDs();
	       List<Long> resultlist = null;
	        while(userIds.hasNext()){
	        	long uid = userIds.next();
	        	long[] similarUsers = neighborhood.getUserNeighborhood(uid);
	        	FastIDSet fsids = dataModel.getItemIDsFromUser(uid);
	        	FastIDSet recommendsids = null;
				try {
					recommendsids = recommendModel.getItemIDsFromUser(uid);
				} catch (TasteException e) {
					//e.printStackTrace();
					System.out.println(uid+":没有已经被推荐的用户");
				}
	        	int initcount = 0;
	        	//排除已经是好友关系的相似用户，并找出10个最相似的好友
//	        	int size = similarUsers.length>count?count:similarUsers.length;
	        	resultlist = new ArrayList<Long>();
	        	for(long similarUser:similarUsers){
	        		//是否已经是好友
	        		if(!fsids.contains(similarUser)){
	        			if(null != recommendsids){
	        				if(!recommendsids.contains(similarUser)){
		        				if(initcount>=count){
		        					break;
		        				}else{
		        					resultlist.add(similarUser);
		        					initcount++;
		        				}
		        			}
	        			}else{
	        				if(initcount>=count){
	        					break;
	        				}else{
	        					resultlist.add(similarUser);
	        					initcount++;
	        				}
	        			}
	        		}
	        	}
	        	
	        	//查找好友的共同好友
	        	FastIDSet rds = null;
	        	FastIDSet crds = null;
	        	RecommendInfo[] recommendInfos = new RecommendInfo[resultlist.size()];
	        	int i = 0;
	        	for(long rcmuid:resultlist){
	        		System.out.println(uid+"与"+rcmuid+"的共同好友");
	        		rds = dataModel.getItemIDsFromUser(rcmuid);
	        		System.out.println(rcmuid+"的好友"+rds);
	        		System.out.println(uid+"的好友"+fsids);
	        		crds = rds.clone();
	        		
	        		System.out.println(rcmuid+"克隆的好友:"+crds);
//	        		rds = null;
	        	
	        		crds.retainAll(fsids);
	        		RecommendInfo recommendInfo = new RecommendInfo();
	        		recommendInfo.setUserId(rcmuid);
	        		recommendInfo.setCommonFriends(crds.toArray());
	        		recommendInfos[i] = recommendInfo;
//	        		crds = null;
	        		i++;
	        	}
	        	
	        	//insertOrUpdate相似好友
	            mongoOperations.upsert(new Query(Criteria.where("userId").is(uid)), new Update().update("recomeneds",recommendInfos), User_Relationship_Recommend.class);
	        }
	}

}
