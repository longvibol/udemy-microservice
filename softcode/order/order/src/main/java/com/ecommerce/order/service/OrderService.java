package com.ecommerce.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.order.dto.OrderItemDTO;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.model.OrderStatus;
import com.ecommerce.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final CartService cartService;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(Long userId) {
    	
    	/*
        // Validate user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();
        */

        // Validate for cart items (what we have add)
        List<CartItem> cartItems = cartService.getCart(userId);

        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        // Calculate total price of order

        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        // convert from cartItem to orderItem
        List<OrderItem> orderItems = cartItems.stream()
                .map(item-> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                )).toList();
        order.setItems(orderItems);
        Order saveOrder = orderRepository.save(order);

        // Clear the cart
        cartService.clearCart(userId);

        // want to return what is saveOrder to the database and it is the Optional of Optional<OrderResponse>
        return Optional.of(mapToOrderResponse(saveOrder));
    }

    public OrderResponse mapToOrderResponse(Order order) {

        List<OrderItemDTO> items = order.getItems().stream()
                .map(orderItem -> new OrderItemDTO(
                        orderItem.getId(),
                        orderItem.getProductId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(),
                        orderItem.getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                items,
                order.getCreatedAt()
        );
    }

}