package com.example.ecommerce.rest.controller;

import com.example.ecommerce.rest.model.Customer;
import com.example.ecommerce.rest.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CustomerController customerController = new CustomerController();
        // Use reflection to set the repository
        try {
            java.lang.reflect.Field field = CustomerController.class.getDeclaredField("customerRepository");
            field.setAccessible(true);
            field.set(customerController, customerRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers_ShouldReturnCustomers() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAddress("123 Main St");
        customer.setPhone("+1-234-567-8900");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    public void getCustomerById_ShouldReturnCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setAddress("123 Main St");
        customer.setPhone("+1-234-567-8900");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void createCustomer_ShouldReturnCreatedCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        customer.setEmail("jane@example.com");
        customer.setAddress("456 Oak St");
        customer.setPhone("+1-234-567-8901");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setName(customer.getName());
        savedCustomer.setEmail(customer.getEmail());
        savedCustomer.setAddress(customer.getAddress());
        savedCustomer.setPhone(customer.getPhone());

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }

    @Test
    public void updateCustomer_ShouldReturnUpdatedCustomer() throws Exception {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setName("John Doe");
        existingCustomer.setEmail("john@example.com");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        updatedCustomer.setName("John Updated");
        updatedCustomer.setEmail("john.updated@example.com");
        updatedCustomer.setAddress("789 Pine St");
        updatedCustomer.setPhone("+1-234-567-8902");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(put("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"));
    }

    @Test
    public void deleteCustomer_ShouldReturnNoContent() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isOk());
    }
}