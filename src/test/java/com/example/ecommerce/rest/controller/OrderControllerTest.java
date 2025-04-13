package com.example.ecommerce.rest.controller;

import com.example.ecommerce.rest.model.Order;
import com.example.ecommerce.rest.model.Customer;
import com.example.ecommerce.rest.model.Product;
import com.example.ecommerce.rest.repository.OrderRepository;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        OrderController orderController = new OrderController();
        // Use reflection to set the repository
        try {
            java.lang.reflect.Field field = OrderController.class.getDeclaredField("orderRepository");
            field.setAccessible(true);
            field.set(orderController, orderRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    private Order createSampleOrder() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("99.99"));

        Set<Product> products = new HashSet<>();
        products.add(product);

        Order order = new Order();
        order.setId(1L);
        order.setCustomer(customer);
        order.setProducts(products);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setTotalAmount(new BigDecimal("99.99"));

        return order;
    }

    @Test
    public void getAllOrders_ShouldReturnOrders() throws Exception {
        Order order = createSampleOrder();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("PENDING"))
                .andExpect(jsonPath("$[0].customer.id").value(1));
    }

    @Test
    public void getOrderById_ShouldReturnOrder() throws Exception {
        Order order = createSampleOrder();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.customer.id").value(1));
    }

    @Test
    public void createOrder_ShouldReturnCreatedOrder() throws Exception {
        Order order = createSampleOrder();
        order.setId(null); // New order won't have ID

        Order savedOrder = createSampleOrder(); // With ID
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    public void updateOrder_ShouldReturnUpdatedOrder() throws Exception {
        Order existingOrder = createSampleOrder();
        
        Order updatedOrder = createSampleOrder();
        updatedOrder.setStatus("PROCESSING");
        updatedOrder.setTotalAmount(new BigDecimal("199.98"));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        mockMvc.perform(put("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PROCESSING"))
                .andExpect(jsonPath("$.totalAmount").value(199.98));
    }

    @Test
    public void updateOrderStatus_ShouldReturnUpdatedOrder() throws Exception {
        Order existingOrder = createSampleOrder();
        
        Order updatedOrder = createSampleOrder();
        updatedOrder.setStatus("COMPLETED");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        mockMvc.perform(put("/api/orders/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"COMPLETED\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    public void deleteOrder_ShouldReturnNoContent() throws Exception {
        Order order = createSampleOrder();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isOk());
    }
}