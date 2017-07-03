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
import static com.mongodb.client.model.Filters.*;

/**
 * @author aagarwal
 *
 */
public class Pagination {
	
	private static final int RECORDS_PER_PAGE = 10;
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
			);
		}
		collection.insertMany(docList);							
		Block<Document> printBlock = new Block<Document>() {
		     @Override
		     public void apply(final Document document) {
		         System.out.println(document.toJson());
		     }
		};	
		
		for (int pageNumber = 1 ; pageNumber < 5; pageNumber++ ){
			System.out.println("---------------------------------------------------------------------------------");
			collection
				.find(gt("A", 10))
				.limit(RECORDS_PER_PAGE)
				.skip(((pageNumber-1)*RECORDS_PER_PAGE))
				.forEach(printBlock);						
		}
		mongoClient.close();
	}
}
