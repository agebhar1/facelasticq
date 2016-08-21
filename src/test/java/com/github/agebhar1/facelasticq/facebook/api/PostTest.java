package com.github.agebhar1.facelasticq.facebook.api;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class PostTest {
	
	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void test() {
		
		final Post post = Post.builder().id("123456").build();
		
		final Set<ConstraintViolation<Post>> constraintViolations = validator.validate(post);
		
		assertThat(constraintViolations, Matchers.hasSize(1));
		constraintViolations.forEach(x -> {
			System.out.println(x);
		});
		
//		assertThat(post, is(notNullValue()));
		
	}

}
