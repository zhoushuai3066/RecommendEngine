package com.busap.map;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class GroupByUserIdMap extends Mapper<Object, Text, Text, Text>{
	
	private IntWritable v = new IntWritable(0);
	
	
	protected void map(Object key,Text value,Context context) throws IOException, InterruptedException{
			String[] strs = value.toString().split(",");
			context.write(new Text(strs[0]),new Text(strs[1]));
	}

}
