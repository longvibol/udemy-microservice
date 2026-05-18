package com.ecommerce.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.order.dto.OrderItemDTO;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.dto.UserRepository;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.model.OrderStatus;
import com.ecommerce.order.model.User;
import com.ecommerce.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final UserService userService;

    public Optional<OrderResponse> createOrder(String userId) {

        // Validate for cart items (what we have add)
        List<CartItem> cartItems = cartService.getCart(userId);

        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        // Validate user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        // Calculate total price of order

        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = new Order();

        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        // convert from cartItem to orderItem
        List<OrderItem> orderItems = cartItems.stream()
                .map(item-> new OrderItem(
                        null,
                        item.getProduct(),
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
                        orderItem.getProduct().getId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(),
                        orderItem.getPrice()
                                .multiply(new BigDecimal(orderItem.getQuantity()))
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