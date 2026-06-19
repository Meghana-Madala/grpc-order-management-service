package com.meghana.practice.order_management.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderGrpcServiceTest {

    private static ManagedChannel channel;
    private static OrderServiceGrpc.OrderServiceBlockingStub stub;

    @BeforeAll
    static void setup() {

        channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        stub = OrderServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    static void tearDown() {

        channel.shutdown();
    }

    @Test
    void createOrder_shouldReturnSuccess() {

        CreateOrderRequest request =
                CreateOrderRequest.newBuilder()
                        .setCustomerId("TEST001")
                        .setProductName("Laptop")
                        .setQuantity(1)
                        .setPrice(1000.0)
                        .build();

        CreateOrderResponse response =
                stub.createOrder(request);

        assertNotNull(response);
        assertNotNull(response.getOrder().getOrderId());

        assertEquals(
                "TEST001",
                response.getOrder().getCustomerId());
    }

    @Test
    void createOrder_shouldReturnInvalidArgument() {

        CreateOrderRequest request =
                CreateOrderRequest.newBuilder()
                        .setCustomerId("")
                        .setProductName("Laptop")
                        .setQuantity(1)
                        .setPrice(1000.0)
                        .build();

        StatusRuntimeException exception =
                assertThrows(
                        StatusRuntimeException.class,
                        () -> stub.createOrder(request));

        assertTrue(
                exception.getMessage()
                        .contains("Customer ID is required"));
    }

    @Test
    void getOrder_shouldReturnNotFound() {

        StatusRuntimeException exception =
                assertThrows(
                        StatusRuntimeException.class,
                        () -> stub.getOrder(
                                GetOrderRequest.newBuilder()
                                        .setOrderId("INVALID_ID")
                                        .build()));

        assertTrue(
                exception.getMessage()
                        .contains("Order not found"));
    }
}