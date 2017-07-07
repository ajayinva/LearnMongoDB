package com.awsaces.learn.mongodb.spring;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearnMongoDbApplication implements CommandLineRunner {	
	@Autowired
	private ProductRepository productRepository;	
	@Autowired
	private SectionRepository sectionRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(LearnMongoDbApplication.class, args);		
	}	
	@Override
	public void run(String... args) throws Exception {
		productRepository.deleteAll();
		sectionRepository.deleteAll();
		questionRepository.deleteAll();
		userRepository.deleteAll();
				
		List<Section> sections = new ArrayList<>();
		sections.add(new Section(1L,"Storage", "S", "S3, Glacier, CloudFront, Storage Gateway"));	
		sections.add(new Section(2L,"EC2", "S", "EC2, EBS, Snapshots"));
		sections.add(new Section(3L,"Databases", "S", "RDS, Redshift, ElastiCache"));
		sections.add(new Section(4L,"VPC", "S", null));
		sections.add(new Section(5L,"IAM", "S", null));
		sections.add(new Section(6L,"High Availibility", "S", "ELB, CloudWatch, Auto Scaling"));
		sections.add(new Section(8L,"Route 53", "S", null));
		sections.add(new Section(9L,"Application Services", "S", "SQS, SNS and SWF"));
		sections.add(new Section(10L,"DynamoDB", "S", null));
		sections.add(new Section(11L,"DevOps", "S", "CloudFormation, Elastic Beanstalk & OpsWork"));
		sections.add(new Section(12L,"Security and Compliance", "S", null));
		sections.add(new Section(13L,"Well Architected Framework", "S", null));
		sections.add(new Section(14L,"Other Services", "S", "AWS Directory Services, AWS Cloud Trail, Amazon Kinesis, Amazon EMR, AWS Data Pipeline, AWS Import/Export, Snowball, AWS Trusted Advisor, AWS Config, Amazon WorkSpaces"));		
		
		List<Section> exams = new ArrayList<>();		
		exams.add(new Section(15L,"AWS Architect Exam 1", "E", ""));
		exams.add(new Section(16L,"AWS Architect Exam 2", "E", ""));
		exams.add(new Section(17L,"AWS Developer Exam 1", "E", ""));
		exams.add(new Section(18L,"AWS Developer Exam 2", "E", ""));
		exams.add(new Section(21L,"AWS Architect Exam 3", "E", ""));
		
		sectionRepository.save(sections);
		sectionRepository.save(exams);
				
		ProductPlan plan1 = new ProductPlan();
		plan1.id = 1L;		
		plan1.productId = 1L;
		plan1.free= true;
		plan1.best = false;
		plan1.price = new BigDecimal(0);
		plan1.features.add("Unlimited Access");
		plan1.features.add("Simulator Preview");
		plan1.features.add("No Section Quizes");
		plan1.features.add("0 Full length Exams");
		plan1.features.add("Complete Explanations");
		plan1.features.add("20 Questions");
		
		sections.forEach(s->plan1.sectionIds.add(s.id));
		
		ProductPlan plan2 = new ProductPlan();
		plan2.id = 2L;		
		plan2.productId = 1L;
		plan2.free= false;
		plan2.best = true;
		plan2.price = new BigDecimal(9.99);
		plan2.months = 4;
		plan2.features.add("120 Days Access");
		plan2.features.add("Full Simulator");
		plan2.features.add("Section Quizes");
		plan2.features.add("3 Full length Exams");
		plan2.features.add("Complete Explanations");
		plan2.features.add("300+ Questions");
			
		sections.forEach(s->plan2.sectionIds.add(s.id));
		exams.forEach(s->plan2.finalExamIds.add(s.id));
				
		Product product = new Product();
		product.id = 1L;
		product.name = "AWS Certified Associate";
		product.description = "Amazon Web Services Certified Solutions Architect - Associate";
		product.plans.add(plan1);
		product.plans.add(plan2);
		product.features.add("300+ Questions");
		product.features.add("3 Full Length Exams");
		product.features.add("Section Quizes");
		product.features.add("Complete Explanation");
		product.features.add("Online Support");
		product.features.add("Performance Dashboard");
		product.features.add("Multi Device Access");
				
		productRepository.save(product);
		
		List<Question> questions = new ArrayList<>();
		
		Question question1 = new Question(
								1L
							,	"Code1"
							, 	"This is Question 1"
							,	1L
							, 	2L
							,	1
							);		
		question1.options.add(new QuestionOption(1L, "Question 1- Option 1", false));
		question1.options.add(new QuestionOption(2L, "Question 1- Option 2", false));
		question1.options.add(new QuestionOption(3L, "Question 1- Option 3", false));
		question1.options.add(new QuestionOption(4L, "Question 1- Option 4", true));
		
		Question question2 = new Question(
				2L
			,	"Code2"
			, 	"This is Question 2"
			,	1L
			, 	2L
			,	1
			);		
		question2.options.add(new QuestionOption(1L, "Question 2- Option 1", false));
		question2.options.add(new QuestionOption(2L, "Question 2- Option 2", false));
		question2.options.add(new QuestionOption(3L, "Question 2- Option 3", false));
		question2.options.add(new QuestionOption(4L, "Question 2- Option 4", true));
		
		
		Question question3 = new Question(
				3L
			,	"Code2"
			, 	"This is Question 2"
			,	1L
			, 	2L
			,	1
			);		
		question3.options.add(new QuestionOption(1L, "Question 3- Option 1", false));
		question3.options.add(new QuestionOption(2L, "Question 3- Option 2", false));
		question3.options.add(new QuestionOption(3L, "Question 3- Option 3", false));
		question3.options.add(new QuestionOption(4L, "Question 3- Option 4", true));
		
		
		questions.add(question1);
		questions.add(question2);
		questions.add(question3);
		
		questionRepository.save(questions);
		
		User user1 = new User("ajayagarwal007@yahoo.com", "$2a$12$XT3LI19hp2uX..nWJYKr7uVo4SoMR3KjLZuUIkXYu4M87XUgY826S", "Ajay Agarwal");
		user1.products.add(new UserProduct(product.id,plan2.id));
		userRepository.save(user1);
		
		User ajay = userRepository.findByUserName("ajayagarwal007@yahoo.com");
		ajay.notes.add(new QuestionNote(1L, "This is an idotic Question"));
		userRepository.save(ajay);		
	}
}
