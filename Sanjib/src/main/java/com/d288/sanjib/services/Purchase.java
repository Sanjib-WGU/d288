package com.d288.sanjib.services;

import com.d288.sanjib.entities.Cart;
import com.d288.sanjib.entities.CartItem;
import com.d288.sanjib.entities.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Purchase {
    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;
}
