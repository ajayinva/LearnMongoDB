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
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;

/**
 * @author aagarwal
 *
 */
public class Sort {
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
		for(int i = 0 ; i < 10; i ++){
			for(int j = 0 ; j < 10; j ++){
				docList.add(
					new Document("A", i).append("B", j).append("C", true)
				);
			}
		}
		collection.insertMany(docList);
		System.out.println("---------------------------------------------------------------------------------");
		collection
		.find(and(gte("A", 7), lte("B", 5)))
		.projection(fields(include("A", "B"), excludeId()))
		.sort(new Document("A", 1).append("B", -1))
		.forEach(printBlock);
		System.out.println("---------------------------------------------------------------------------------");
		collection
		.find(and(gte("A", 7), lte("B", 5)))
		.projection(fields(include("A", "B"), excludeId()))
		.sort(ascending("A"))
		.forEach(printBlock);
		System.out.println("---------------------------------------------------------------------------------");
		collection
		.find(and(gte("A", 7), lte("B", 5)))
		.projection(fields(include("A", "B"), excludeId()))
		.sort(descending("B"))
		.forEach(printBlock);
		System.out.println("---------------------------------------------------------------------------------");
		collection
		.find(and(gte("A", 7), lte("B", 5)))
		.projection(fields(include("A", "B"), excludeId()))
		.sort(fields(ascending("B"), descending("A")))
		.forEach(printBlock);
		System.out.println("---------------------------------------------------------------------------------");
		mongoClient.close();
	}
}
