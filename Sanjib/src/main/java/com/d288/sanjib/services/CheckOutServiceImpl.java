package com.d288.sanjib.services;

import com.d288.sanjib.dao.CartItemRepository;
import com.d288.sanjib.dao.CartRepository;
import com.d288.sanjib.dao.CustomerRepository;
import com.d288.sanjib.entities.Cart;
import com.d288.sanjib.entities.CartItem;
import com.d288.sanjib.entities.Customer;
import com.d288.sanjib.entities.StatusType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckOutServiceImpl implements CheckOutService {
    private CustomerRepository customerRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    public CheckOutServiceImpl(
            CustomerRepository customerRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }
    
    @Override
    @Transactional
    public PurchaseResponse checkOut(Purchase purchase) {

        Customer customer = purchase.getCustomer();
        Cart cart = purchase.getCart();
        Set<CartItem> cartItems = purchase.getCartItems();

        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);
        cart.setStatus(StatusType.ordered);

        cartItems.forEach(cartItem -> {
            cartItem.setCart(cart);
            cart.add(cartItem);
        });
        cartRepository.save(cart);

// the customer table does not have a unique email to check if the customer exits,
// so commenting out this line for now
//        customerRepository.save(customer);

        PurchaseResponse purchaseResponse = new PurchaseResponse();
        purchaseResponse.setOrderTrackingNumber(orderTrackingNumber);
        return purchaseResponse;
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}