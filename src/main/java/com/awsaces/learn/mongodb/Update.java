/**
 * 
 */
package com.awsaces.learn.mongodb;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

/**
 * @author aagarwal
 *
 */
public class Update {
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
		System.out.println("----------------------------Before Updates-----------------------------------");				
		collection
			.find()
			.forEach(printBlock);	
		
		
		collection.updateOne(
			eq("A",  1)
		, 	combine(set("A", 11), inc("B", 5),set("C",false),currentDate("lastModified"))
		);
		System.out.println("----------------------------After  Updates-----------------------------------");	
		collection
			.find()
			.forEach(printBlock);	
		System.out.println("-----------------------------------------------------------------------------");
		collection.updateMany(
			gt("A",  3)
		, 	combine(set("A", 101), inc("B", 15),set("C",false),currentDate("lastModified"))
		);
		collection
			.find()
			.forEach(printBlock);	
		System.out.println("-----------------------------------------------------------------------------");
		collection.updateOne(
			and(eq("A",  101), eq("B", 31))
		, 	combine(set("A", 500), inc("B", 5),set("C",false),currentDate("lastModified"))
		);
		collection
		.find()
		.forEach(printBlock);	
		System.out.println("-----------------------------------------------------------------------------");
		collection.updateOne(
			eq("A",  700)
		, 	combine(set("A", 700), inc("B", 5),set("C", true),currentDate("lastModified"))
		,	 new UpdateOptions().upsert(true)
		);
		collection
			.find()
			.forEach(printBlock);	
		System.out.println("-----------------------------------------------------------------------------");
		mongoClient.close();
	}
}
