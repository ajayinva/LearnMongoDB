package com.awsaces.learn.mongodb.spring;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * 
 * @author aagarwal
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	User findByUserName(String username);
}
