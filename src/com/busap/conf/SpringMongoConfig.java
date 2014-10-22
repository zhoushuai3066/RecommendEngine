package com.busap.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.Mongo;

@Configuration
public class SpringMongoConfig {
	
	private String MDB_IP = "192.168.108.147";
	private String MDB_NAME= "logdb";
	private String MDB_USER=System.getProperty("mdbuser");
	private String MDB_PASS=System.getProperty("mdbpass");
	

    public Mongo mongo() throws Exception {
    	
        return new Mongo(MDB_IP);
    }
    
    private UserCredentials userCredentials(){
    	return new UserCredentials(MDB_USER, MDB_PASS);
    }
    
    public @Bean  
    MongoDbFactory mongoDbFactory() throws Exception {  
    	return new SimpleMongoDbFactory(mongo(), MDB_NAME);  
    }  

    
	@Bean
    public  MongoOperations mongoTemplate() throws Exception {
		 new SimpleMongoDbFactory(new Mongo(), "database");  
		 MappingMongoConverter converter =  new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());  
		 converter.setTypeMapper(new DefaultMongoTypeMapper(null));  
		  MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);  
//		 return new MongoTemplate(new SimpleMongoDbFactory(mongo(), MDB_NAME, userCredentials()));
//		 return new MongoTemplate(new SimpleMongoDbFactory(mongo(), MDB_NAME));
		  return mongoTemplate;
    }

}
