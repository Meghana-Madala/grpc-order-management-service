package com.meghana.practice.order_management.client;

import com.meghana.practice.order_management.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Scanner;

public class OrderClient {
    public static void main(String[] args) {

        ManagedChannel channel =
                ManagedChannelBuilder
                        .forAddress("localhost", 9090)
                        .usePlaintext()
                        .build();

        OrderServiceGrpc.OrderServiceBlockingStub stub =
                OrderServiceGrpc.newBlockingStub(channel);

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== ORDER MANAGEMENT CLIENT =====");
            System.out.println("1. Create Order");
            System.out.println("2. Get Order");
            System.out.println("3. Update Order");
            System.out.println("4. Delete Order");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            try {

                switch (choice) {

                    case 1 -> {

                        System.out.print("Customer ID: ");
                        String customerId = scanner.nextLine();

                        System.out.print("Product Name: ");
                        String productName = scanner.nextLine();

                        System.out.print("Quantity: ");
                        int quantity = Integer.parseInt(scanner.nextLine());

                        System.out.print("Price: ");
                        double price = Double.parseDouble(scanner.nextLine());

                        CreateOrderRequest request =
                                CreateOrderRequest.newBuilder()
                                        .setCustomerId(customerId)
                                        .setProductName(productName)
                                        .setQuantity(quantity)
                                        .setPrice(price)
                                        .build();

                        CreateOrderResponse response =
                                stub.createOrder(request);

                        System.out.println("\nOrder Created Successfully:");
                        System.out.println(response.getOrder());
                    }

                    case 2 -> {

                        System.out.print("Order ID: ");
                        String orderId = scanner.nextLine();

                        GetOrderResponse response =
                                stub.getOrder(
                                        GetOrderRequest.newBuilder()
                                                .setOrderId(orderId)
                                                .build());

                        System.out.println("\nOrder Details:");
                        System.out.println(response.getOrder());
                    }

                    case 3 -> {

                        System.out.print("Order ID: ");
                        String orderId = scanner.nextLine();

                        System.out.print("Customer ID: ");
                        String customerId = scanner.nextLine();

                        System.out.print("Product Name: ");
                        String productName = scanner.nextLine();

                        System.out.print("Quantity: ");
                        int quantity = Integer.parseInt(scanner.nextLine());

                        System.out.print("Price: ");
                        double price = Double.parseDouble(scanner.nextLine());

                        System.out.println("Status Options:");
                        System.out.println("CREATED");
                        System.out.println("CONFIRMED");
                        System.out.println("SHIPPED");
                        System.out.println("DELIVERED");
                        System.out.println("CANCELLED");

                        System.out.print("Status: ");
                        String statusInput = scanner.nextLine();

                        UpdateOrderRequest request =
                                UpdateOrderRequest.newBuilder()
                                        .setOrderId(orderId)
                                        .setCustomerId(customerId)
                                        .setProductName(productName)
                                        .setQuantity(quantity)
                                        .setPrice(price)
                                        .setStatus(OrderStatus.valueOf(statusInput.toUpperCase()))
                                        .build();

                        UpdateOrderResponse response =
                                stub.updateOrder(request);

                        System.out.println("\nOrder Updated Successfully:");
                        System.out.println(response.getOrder());
                    }

                    case 4 -> {

                        System.out.print("Order ID: ");
                        String orderId = scanner.nextLine();

                        DeleteOrderResponse response =
                                stub.deleteOrder(
                                        DeleteOrderRequest.newBuilder()
                                                .setOrderId(orderId)
                                                .build());

                        System.out.println(response.getMessage());
                    }

                    case 5 -> {

                        System.out.println("Exiting application...");

                        scanner.close();
                        channel.shutdown();

                        return;
                    }

                    default -> System.out.println("Invalid option. Try again.");
                }

            } catch (Exception ex) {

                System.out.println("\nOperation Failed:");
                System.out.println(ex.getMessage());
            }
        }
    }
}
