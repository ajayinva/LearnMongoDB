/**
 * 
 */
package com.awsaces.learn.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Projections.*;

import static com.mongodb.client.model.Filters.*;

/**
 * @author aagarwal
 *
 */
public class Projections {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient(); 
		MongoDatabase mongoDatabase = mongoClient.getDatabase("learn-mongodb");
		MongoCollection<Document> collection = mongoDatabase.getCollection("test");
		collection.drop();					
		
		Block<Document> printBlock = new Block<Document>() {
		     @Override
		     public void apply(final Document document) {
		         System.out.println(document.toJson());
		     }
		};	
		
		List<Document> docList = new ArrayList<>();
		for(int i = 0 ; i<100;i ++){
			docList.add(
				new Document("A", i)
					.append("B", i+10)
					.append("C", true)
			);
		}
		collection.insertMany(docList);				
		collection
			.find(or(gte("A", 95), lte("B", 11)))
			.projection(new Document("A", 0).append("_id", 0))
			.forEach(printBlock);
		System.out.println("---------------------------------------------------------------------------------");
		collection
			.find(or(gte("A", 95), lte("B", 11)))
			.projection(fields(include("A"),include("B"), excludeId()))
			.forEach(printBlock);
		System.out.println("---------------------------------------------------------------------------------");
		collection
		.find(or(gte("A", 95), lte("B", 11)))
		.projection(fields(include("A", "B"), excludeId()))
		.forEach(printBlock);
		mongoClient.close();
	}
}
