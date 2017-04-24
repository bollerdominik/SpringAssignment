package com.example;

import com.example.domain.Employee;
import com.example.domain.Shift;
import com.example.domain.respository.EmployeeRepository;
import com.example.domain.respository.ShiftRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DemoApplicationTests {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	private MediaType contentTypeHal = new MediaType(MediaTypes.HAL_JSON.getType(),
			MediaTypes.HAL_JSON.getSubtype(),
			Charset.forName("utf8"));


	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	private String token;
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private EmployeeRepository repositoryEmp;
	@Autowired
	private ShiftRepository repositoryShift;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);

		assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}



	@Test
	public void t1defaultEmployee() throws Exception {
		mockMvc.perform(get("/api/employees"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$",hasSize(1)))
				.andExpect(jsonPath("[0].id",is(1)))
				.andExpect(jsonPath("[0].firstName",is("Mike")))
				.andExpect(jsonPath("[0].lastName",is("Hunt")))
				.andExpect(jsonPath("[0].shifts",hasSize(1)))
				.andExpect(jsonPath("[0].shifts[0].id",is(1)))
				.andExpect(jsonPath("[0].shifts[0].shift",is("EVENING")));
	}

	@Test
	public void t2addEmployee() throws Exception {
		Employee emp = new Employee("Lars","Kum", Employee.Days.MONDAY, new ArrayList<>());

		// Add employee
		mockMvc.perform(post("/api/employee/save")
				.param("firstName",emp.getFirstName())
				.param("lastName",emp.getLastName())
				.param("day",emp.getDay().toString())
				.param("shiftIds[]","1"))
                .andExpect(status().isOk());

		// Verify employee was added to employees
		mockMvc.perform(get("/api/employees"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$",hasSize(2)))
				.andExpect(jsonPath("[1].shifts",hasSize(1)))
				.andExpect(jsonPath("[0].shifts[0].id",is(1)))
				.andExpect(jsonPath("[0].shifts[0].shift",is("EVENING")))
				.andExpect(jsonPath("[1].firstName",is(emp.getFirstName())))
				.andExpect(jsonPath("[1].lastName",is(emp.getLastName())));

		// Verify employee was added to shifts
		mockMvc.perform(get("/api/shifts/1/employees"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentTypeHal))
				.andExpect(jsonPath("_embedded.employees",hasSize(2)))
				.andExpect(jsonPath("_embedded.employees[1].firstName",is(emp.getFirstName())))
				.andExpect(jsonPath("_embedded.employees[1].lastName",is(emp.getLastName())));



	}

	@Test
	public void t3addShiftToEmployee() throws Exception {
		Employee emp = new Employee("Rust","Summer", Employee.Days.MONDAY, new ArrayList<>());

		// Add employee
		mockMvc.perform(post("/api/employee/save")
				.param("firstName",emp.getFirstName())
				.param("lastName",emp.getLastName())
				.param("day",emp.getDay().toString())
				.param("shiftIds[]","2"))
				.andExpect(status().isOk());


		// Assign Shift
		mockMvc.perform(put("/api/employees/"+3+"/shifts/"+3))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());

		mockMvc.perform(get("/api/employees"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("[2].shifts",hasSize(2)));

		// Assign Shift
		mockMvc.perform(put("/api/employees/"+3+"/shifts/"+4))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());

		mockMvc.perform(get("/api/employees"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("[2].shifts",hasSize(3)));

	}

	@Test
	public void t4deleteEmployee() throws Exception {

		mockMvc.perform(delete("/api/employees/1"))
				.andExpect(status().isNoContent());

		// Verify Mark Hunt was removed
		mockMvc.perform(get("/api/employees"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$",hasSize(2)))
				.andExpect(jsonPath("[0].id",is(2)))
				.andExpect(jsonPath("[0].firstName",not("Mike")))
				.andExpect(jsonPath("[0].lastName",not("Hunt")));
	}



}
