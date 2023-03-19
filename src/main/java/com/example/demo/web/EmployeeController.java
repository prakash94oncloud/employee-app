package com.example.demo.web;

import com.example.demo.EmployeeAppApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.data.models.Employee;
import com.example.demo.data.payloads.request.EmployeeRequest;
import com.example.demo.data.payloads.response.MessageResponse;
import com.example.demo.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	@GetMapping("/hello")
	public ResponseEntity<List<String>> get(){
		List<String> list = new ArrayList<>();
		list.add("Hello World");
		list.add("InstanceId="+EmployeeAppApplication.getProp("instanceId"));
		list.add("Private IP="+EmployeeAppApplication.getProp("privateIP"));
		list.add("Public IP="+EmployeeAppApplication.getProp("publicIP"));

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		List<Employee> listAllEmployees = employeeService.getAllEmployees();
		return new ResponseEntity<>(listAllEmployees, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer id){
		Employee employee = employeeService.getAsSingleEmployee(id);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<MessageResponse> addEmployee(@RequestBody EmployeeRequest employeeRequest){
		MessageResponse messageResponse = employeeService.createEmployee(employeeRequest);
		return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<MessageResponse> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest){
		Optional<Employee> employee = employeeService.updateEmployee(id, employeeRequest);
		return new ResponseEntity<>(new MessageResponse("Employee has updated successfully!!"), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") Integer id){
		employeeService.deletEmployee(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
