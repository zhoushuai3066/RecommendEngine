package com.busap.main;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
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
import com.busap.model.ZhouModel;

public class TRecommender {
	
	private static ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	private static MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate",MongoOperations.class);
//	private static MongoOperations mongoOperations2 = (MongoOperations) ctx.getBean("logDbMongoTemplate",MongoOperations.class);
	
	
	public static void main( String[] args ) throws IOException, TasteException 
    {
//		DateFormat df = DateFormat.getDateInstance();
//		ZhouModel mm = new ZhouModel("192.168.109.240",27017,"registrar","mrecommend_log",false,false,df);
//		LongPrimitiveIterator uids = mm.getUserIDs();
//		while(uids.hasNext()){
//			System.out.println(mm.fromLongToId(uids.next()));
//			 
//			FastIDSet fs = mm.getItemIDsFromUser(uids.next());
//			LongPrimitiveIterator it = fs.iterator();
//			while(it.hasNext()){
//				System.out.println(mm.fromLongToId(it.next()));
//			}
//		}
//		LongPrimitiveIterator ids = mm.getItemIDsFromUser(0).iterator();
//		while(ids.hasNext()){
//			System.out.println(ids.next());
//		}
//		List<Long> outer = new ArrayList<Long>();
//		outer.add(1L);
//		outer.add(2L);
//		outer.add(3L);
//		outer.add(4L);
//		outer.add(5L);
//		outer.add(7L);
//		
		 String path  = "G:/mahoutworkspace/zhoushuai/data/tanimotoCoefficientSimilarityData.txt";
		    File f = new File(path);
		   @SuppressWarnings("unused")
		DataModel dataModel = new FileDataModel(f);
		   
		 //查找好友的共同好友
       	FastIDSet rds = null;
       	FastIDSet crds = null;
       	
       	RecommendInfo[] recommendInfos = new RecommendInfo[2];
       	RecommendInfo recommendInfo1 = new RecommendInfo();
       	recommendInfo1.setUserId(2);
       	long[] lon1 = {5,6,7};
       	recommendInfo1.setCommonFriends(lon1);
       	recommendInfos[0] = recommendInfo1;
       	RecommendInfo recommendInfo2 = new RecommendInfo();
       	recommendInfo2.setUserId(3);
       	long[] lon2 = {6,7,8};
       	recommendInfo2.setCommonFriends(lon2);
       	recommendInfos[1] = recommendInfo2;
       	
       	
        mongoOperations.upsert(new Query(Criteria.where("userId").is(1)), new Update().update("recomeneds",recommendInfos), User_Relationship_Recommend.class);
//		   List brr = Arrays.asList(br);
//		   arr.retainAll(brr);
//		   System.out.println(arr);
//		   System.out.println(dataModel.getItemIDsFromUser(2));
//	       UserSimilarity userSimilarity = new TanimotoCoefficientSimilarity(dataModel);  
//	       UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, userSimilarity, dataModel);  
//	       
//	       
//	       LongPrimitiveIterator userIds = dataModel.getUserIDs();
//	       userIds.skip(0);
//	       
//	       
//	       LongPrimitiveIterator userIds2 = dataModel.getUserIDs();
//	       userIds2.skip(50);
	       
//	       System.out.println("22");
	       
//		
//		List<Long> inner = new ArrayList<Long>();
//		inner.add(10L);
//		inner.add(11L);
//		inner.add(12L);
//		inner.add(13L);
//		inner.add(14L);
//		inner.add(15L);
//		inner.add(16L);
//		
//		Iterator<Long> it = outer.iterator();
//		
//		while(it.hasNext()){
//			Long outerl = it.next();
//			System.out.println(outerl);
//			for(Long innerl :inner){
//				System.out.println(innerl<12L);
//				if(innerl==12L){
//					break;
//				}else{
//					System.out.println(innerl);
//				}
//			}
//		}
		
//		 long[] ls = new long[2];
//		 ls[0] = 1L;
//		 ls[1] = 2L;
//		 mongoOperations.upsert(new Query(Criteria.where("userId").is(12L)), new Update().update("recomeneds", ls), Recommend.class);
//		
    }

}
