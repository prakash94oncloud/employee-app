package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.joda.time.LocalTime;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.util.EC2MetadataUtils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class EmployeeAppApplication extends SpringBootServletInitializer{

	@Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(EmployeeAppApplication.class);
    }

    private static Map demomap = new HashMap();
    public static String getProp(String propName){
		return (String)demomap.get(propName);
	}

	public static void main(String[] args) {
		SpringApplication.run(EmployeeAppApplication.class, args);

		String instanceId = EC2MetadataUtils.getInstanceId();
		LocalTime currentTime = new LocalTime();
		System.out.println("The current local time is: " + currentTime);

		// Getting EC2 private IP
		String privateIP = EC2MetadataUtils.getInstanceInfo().getPrivateIp();
		System.out.println("Private IP:"+ privateIP);

		AmazonEC2 awsEC2client = AmazonEC2ClientBuilder.defaultClient();
		String publicIP = awsEC2client.describeInstances(new DescribeInstancesRequest()
							 .withInstanceIds(instanceId))
								.getReservations()
								.stream()
								.map(Reservation::getInstances)
								.flatMap(List::stream)
								.findFirst()
								.map(Instance::getPublicIpAddress)
								.orElse(null);

		System.out.println("Public IP:"+ publicIP);
		LocalTime currentTime2 = new LocalTime();
		System.out.println("The current local time is: " + currentTime2);
		demomap.put("instanceId",instanceId);
		demomap.put("privateIP",privateIP);
		demomap.put("publicIP",publicIP);
	}

}
