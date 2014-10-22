package com.busap.map;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.bson.BSONObject;

public class RecommendedTableReadMap extends Mapper<Object, BSONObject, IntWritable, IntWritable>{
	
	
	protected void map(Object key,BSONObject value,Context context) throws IOException, InterruptedException{
	    int user =  (int) value.get("_id");
	    String item =  (String) value.get("ids");
	    String[] items = item.split(",");
	    for(String i:items){
	    	context.write(new IntWritable(user), new IntWritable(Integer.parseInt(i)));
	    }
	}

}
