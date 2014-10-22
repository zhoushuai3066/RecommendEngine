package com.busap.map;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.bson.BSONObject;

public class MUserRelationTableReadMap extends Mapper<Object, BSONObject, IntWritable, IntWritable>{
	
	
	protected void map(Object key,BSONObject value,Context context) throws IOException, InterruptedException{
	    int user =  (int) value.get("uid");
	    int item =  (int) value.get("followid");
	    context.write(new IntWritable(user), new IntWritable(item));
	}

}
