package com.busap.reduce;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


public class RecommendByUserIdReduce extends Reducer<Text, Text, Text, Text>{
	
	private FastByIDMap<FastIDSet> pre = new FastByIDMap<FastIDSet>();
	private FastIDSet fset;
	
	
	
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		super.setup(context);
	}



	protected void reduce(Text key,Iterable<Text> values,Context context) throws IOException, InterruptedException {
//		String userId = key.toString();
//		Iterator<Text> it = values.iterator();
////		while(it.hasNext()){
////			Text value = it.next();
////			sum+=value.get();
////		}
//        fset = new FastIDSet();
//    	fset.add(101);
//    	fset.add(102);
//    	fset.add(103);
//    	pre.put(1L, fset);
//    	
//    	DataModel model = new GenericBooleanPrefDataModel(pre);
//    	
//    	
////		DataModel dataModel = new FileDataModel(f);
//		 UserSimilarity userSimilarity = new TanimotoCoefficientSimilarity(dataModel); 
//		 UserNeighborhood neighborhood;
//		try {
//			neighborhood = new NearestNUserNeighborhood(10, userSimilarity, dataModel);
//			long[] arg = neighborhood.getUserNeighborhood(Long.parseLong(userId));
//			for(int i=0;i<arg.length;i++){
//				System.out.println("用户"+1+"和用户"+arg[i]+"的相似性是:"+userSimilarity.userSimilarity(1, arg[i]));
//			}
//		} catch (TasteException e) {
//			e.printStackTrace();
//		} 
		context.write(key, new Text(""));
	}

}
