package com.meghana.practice.order_management.grpc;

import com.meghana.practice.order_management.exception.InvalidOrderException;
import com.meghana.practice.order_management.exception.OrderNotFoundException;
import com.meghana.practice.order_management.service.OrderService;
import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;
import com.meghana.practice.order_management.model.Order;

import io.grpc.Status;

@GrpcService
public class OrderGrpcService extends OrderServiceGrpc.OrderServiceImplBase {
    private final OrderService orderService;

    public OrderGrpcService(OrderService orderService) {
        this.orderService = orderService;
    }
    @Override
    public void createOrder(
            CreateOrderRequest request,
            StreamObserver<CreateOrderResponse> responseObserver) {
            try {
                Order order = Order.builder()
                        .customerId(request.getCustomerId())
                        .productName(request.getProductName())
                        .quantity(request.getQuantity())
                        .price(request.getPrice())
                        .build();

                Order savedOrder = orderService.createOrder(order);

                com.meghana.practice.order_management.grpc.Order grpcOrder =
                        com.meghana.practice.order_management.grpc.Order.newBuilder()
                                .setOrderId(savedOrder.getOrderId())
                                .setCustomerId(savedOrder.getCustomerId())
                                .setProductName(savedOrder.getProductName())
                                .setQuantity(savedOrder.getQuantity())
                                .setPrice(savedOrder.getPrice())
                                .setStatus(
                                        com.meghana.practice.order_management.grpc.OrderStatus.valueOf(
                                                savedOrder.getStatus().name()))
                                .build();

                CreateOrderResponse response =
                        CreateOrderResponse.newBuilder()
                                .setOrder(grpcOrder)
                                .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
            catch (InvalidOrderException ex) {

                responseObserver.onError(
                        Status.INVALID_ARGUMENT
                                .withDescription(ex.getMessage())
                                .asRuntimeException());
            }
    }

    @Override
    public void getOrder(
            GetOrderRequest request,
            StreamObserver<GetOrderResponse> responseObserver) {

        try {

            String orderId = request.getOrderId();

            Order order = orderService.getOrder(orderId);

            com.meghana.practice.order_management.grpc.Order grpcOrder =
                    com.meghana.practice.order_management.grpc.Order.newBuilder()
                            .setOrderId(order.getOrderId())
                            .setCustomerId(order.getCustomerId())
                            .setProductName(order.getProductName())
                            .setQuantity(order.getQuantity())
                            .setPrice(order.getPrice())
                            .setStatus(
                                    com.meghana.practice.order_management.grpc.OrderStatus.valueOf(
                                            order.getStatus().name()))
                            .build();

            GetOrderResponse response =
                    GetOrderResponse.newBuilder()
                            .setOrder(grpcOrder)
                            .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (OrderNotFoundException ex) {

            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(ex.getMessage())
                            .asRuntimeException());

        }
    }

    @Override
    public void updateOrder(
            UpdateOrderRequest request,
            StreamObserver<UpdateOrderResponse> responseObserver) {

        try {
            Order updatedOrder = Order.builder()
                    .customerId(request.getCustomerId())
                    .productName(request.getProductName())
                    .quantity(request.getQuantity())
                    .price(request.getPrice())
                    .status(
                            com.meghana.practice.order_management.model.OrderStatus.valueOf(
                                    request.getStatus().name()))
                    .build();

            Order savedOrder = orderService.updateOrder(
                    request.getOrderId(),
                    updatedOrder);

            com.meghana.practice.order_management.grpc.Order grpcOrder =
                    com.meghana.practice.order_management.grpc.Order.newBuilder()
                            .setOrderId(savedOrder.getOrderId())
                            .setCustomerId(savedOrder.getCustomerId())
                            .setProductName(savedOrder.getProductName())
                            .setQuantity(savedOrder.getQuantity())
                            .setPrice(savedOrder.getPrice())
                            .setStatus(
                                    com.meghana.practice.order_management.grpc.OrderStatus.valueOf(
                                            savedOrder.getStatus().name()))
                            .build();

            UpdateOrderResponse response =
                    UpdateOrderResponse.newBuilder()
                            .setOrder(grpcOrder)
                            .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (InvalidOrderException ex) {

            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription(ex.getMessage())
                            .asRuntimeException());
        }
        catch (OrderNotFoundException ex) {

            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(ex.getMessage())
                            .asRuntimeException());
        }
    }

    @Override
    public void deleteOrder(
            DeleteOrderRequest request,
            StreamObserver<DeleteOrderResponse> responseObserver) {

        try {

            String orderId = request.getOrderId();

            orderService.deleteOrder(orderId);

            DeleteOrderResponse response =
                    DeleteOrderResponse.newBuilder()
                            .setMessage("Order deleted successfully")
                            .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (OrderNotFoundException ex) {

            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(ex.getMessage())
                            .asRuntimeException());
        }
    }
}
