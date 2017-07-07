package com.awsaces.learn.mongodb.spring;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Transient;
/**
 * @author aagarwal
 *
 */
public class ProductPlan implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -786950369598975948L;
	
	public Long productId;
	
	public Long id;
	
    public boolean free;
    
    public boolean best;
    
    public BigDecimal price;
    
    public Integer months;
    
    public List<String> features = new ArrayList<>();
    
    public List<Long> sectionIds = new ArrayList<>();
    
    public List<Long> finalExamIds = new ArrayList<>();
    
    @Transient
    public List<Section> sections = new ArrayList<>();
    
    @Transient
    public List<Section> finalExams = new ArrayList<>();
}
