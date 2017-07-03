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
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

/**
 * @author aagarwal
 *
 */
public class Find {
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
		for(int i = 0 ; i<100;i ++){
			docList.add(
				new Document("A", i)
					.append("B", i+10)
					.append("C", true)
			);
		}
		collection.insertMany(docList);				
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println(collection.find().first().toJson());
		System.out.println("---------------------------------------------------------------------------------");
		MongoCursor<Document> cursor1 = collection.find().iterator();
		try {
			while(cursor1.hasNext()){
				Document doc = cursor1.next();
				System.out.println(doc.toJson());
			}
		} finally{
			cursor1.close();
		}
		System.out.println("---------------------------------------------------------------------------------");
		//Find A = 63
		MongoCursor<Document> cursor2 = collection.find(new Document("A", 63)).iterator();
		try {
			while(cursor2.hasNext()){
				Document doc = cursor2.next();
				System.out.println(doc.toJson());
			}
		} finally{
			cursor2.close();
		}
		System.out.println("---------------------------------------------------------------------------------");
		MongoCursor<Document> cursor3 = collection.find(eq("B", 97)).iterator();
		try {
			while(cursor3.hasNext()){
				Document doc = cursor3.next();
				System.out.println(doc.toJson());
			}
		} finally{
			cursor3.close();
		}
		System.out.println("---------------------------------------------------------------------------------");				
		Block<Document> printBlock = new Block<Document>() {
		     @Override
		     public void apply(final Document document) {
		         System.out.println(document.toJson());
		     }
		};		
		collection
			.find(gt("A", 90))
			.forEach(printBlock);		
		System.out.println("---------------------------------------------------------------------------------");		
		collection
			.find(and(gt("A", 90), lte("B", 105)))
			.forEach(printBlock);
		System.out.println("---------------------------------------------------------------------------------");		
		collection
			.find(or(gt("A", 90), lte("B", 20)))
			.forEach(printBlock);
		System.out.println("---------------------------------------------------------------------------------");
		mongoClient.close();
	}
}
