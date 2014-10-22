package com.busap.job;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.busap.map.GroupByUserIdMap;



public class FriendRecommendJob {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		String path = args[0];
		Configuration conf = new Configuration();
		conf.addResource(new Path(path));
        
		//获取数据输入地址
		String inputPath = conf.get("inputPath");
		String outPath = conf.get("outPath");
		Path input = new Path(inputPath);
		Path output = new Path(outPath);
		
		Job job = new Job(conf,"generater friend recommend datasource");
		job.setJarByClass(GenerateMUserRelationDataSourceJob.class);
		
		//设置map
		job.setMapperClass(GroupByUserIdMap.class);
		
		
		//设置输出的key和value数据类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		

	}

}
