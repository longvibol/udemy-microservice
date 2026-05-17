package com.app.ecom.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.order.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
