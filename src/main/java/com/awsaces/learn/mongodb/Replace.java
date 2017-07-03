/**
 * 
 */
package com.awsaces.learn.mongodb;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author aagarwal
 *
 */
public class Replace {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient(); 
		MongoDatabase mongoDatabase = mongoClient.getDatabase("learn-mongodb");
		MongoCollection<Document> collection = mongoDatabase.getCollection("test");
		collection.drop();					
		
		List<Document> docList = new ArrayList<>();
		for(int i = 0 ; i<5;i ++){
			docList.add(
				new Document("A", i)
					.append("B", i+10)
					.append("C", true)
			);
		}
		collection.insertMany(docList);
		Block<Document> printBlock = new Block<Document>() {
		     @Override
		     public void apply(final Document document) {
		         System.out.println(document.toJson());
		     }
		};	
		System.out.println("----------------------------Before Replace-----------------------------------");				
		collection
			.find()
			.forEach(printBlock);	
		
		collection.replaceOne(
			eq("A",  1)
		, 	new Document("name","Ajay").append("age", 40).append("address", "182 Laurel Way").append("dateOfBirth", new Date())
		);
		
		System.out.println("----------------------------After  Replace-----------------------------------");
		collection
			.find()
			.forEach(printBlock);	
		System.out.println("-----------------------------------------------------------------------------");
		mongoClient.close();
	}
}
