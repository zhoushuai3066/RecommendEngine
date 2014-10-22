package com.busap.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mahout.cf.taste.common.TasteException;
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
import com.busap.conf.SpringMongoConfig;

public class SimilarityRecommendThread {

	
	private static ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	private static MongoOperations mongoOperations = (MongoOperations) ctx.getBean(MongoOperations.class);
	
	private static int count = 10;
	
	//每个线程的处理数
	private static final int  workCount = 4;
	
	public static void main(String[] args) throws IOException, TasteException {
//		   String path = args[0];
		    //String path  = "D:/workspace/mahout_1/new.dat";
		String path  = "D:/workspace/mahout_1/helloword.txt";
		    File f = new File(path);
		   DataModel dataModel = new FileDataModel(f);
	       UserSimilarity userSimilarity = new TanimotoCoefficientSimilarity(dataModel);  
	       UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, userSimilarity, dataModel);  
	      // LongPrimitiveIterator userIds = dataModel.getUserIDs();
	       int userCounts=dataModel.getNumUsers();
	       List<LongPrimitiveIterator> listIterators = new ArrayList<LongPrimitiveIterator>();
	       
	       int threadCount = userCounts % workCount == 0 ? userCounts/workCount :userCounts/workCount +1;
	       System.out.println(threadCount);
	       //userIds.skip(2);
	       ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
	       
	       for (int threadId = 1; threadId <= threadCount; threadId++) {
			
	           //每个线程下载的起始位置  
               int startIndex = (threadId - 1) * workCount;  
               //每个线程下载的结束位置  
               int endIndex = threadId*workCount - 1;  
               if(threadId == threadCount){  
                   endIndex = userCounts;  
               } 
               listIterators.add(dataModel.getUserIDs());
               System.out.println("startIndex = " + startIndex +"=== endIndex = " + endIndex);
	    	  executorService.execute(new ISimilarity(dataModel,neighborhood,mongoOperations,listIterators,startIndex,endIndex,threadId-1));
	       }
	       executorService.shutdown();
	}
}
