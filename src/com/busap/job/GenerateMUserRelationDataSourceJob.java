package com.busap.job;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.busap.map.MUserRelationTableReadMap;
import com.mongodb.hadoop.MongoInputFormat;
import com.mongodb.hadoop.util.MongoConfigUtil;



public class GenerateMUserRelationDataSourceJob {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
//		   Mongo mongo = new Mongo("192.168.108.147", 27017);
//		   DB db = mongo.getDB("mkdb");
//		   DBCollection collection = db.getCollection("muser_relation");
//		   System.out.println(collection.count());

		String path = args[0];
		Configuration conf = new Configuration();
		conf.addResource(new Path(path));

		//设置数据的来源mongo的库和表地址
		String mongoIP = conf.get("mongoIP");
		String dbname = conf.get("dbname");
		String inputtable = conf.get("inputtable");
		String mongoinputurl = "mongodb://"+mongoIP+"/"+dbname+"."+inputtable;
		MongoConfigUtil.setInputURI(conf, mongoinputurl);
		
		Job job = new Job(conf,"generater friend recommend datasource");
		job.setJarByClass(GenerateMUserRelationDataSourceJob.class);
		
		
		String outpath = conf.get("outpath");
		Path outFile = new Path(outpath);
		FileOutputFormat.setOutputPath(job, outFile);
		//设置map
		job.setMapperClass(MUserRelationTableReadMap.class);
		
		
		//设置输出的key和value数据类型
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setInputFormatClass(MongoInputFormat.class);  
		job.setOutputFormatClass(TextOutputFormat.class);
		
		
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
		
		
	}

}
