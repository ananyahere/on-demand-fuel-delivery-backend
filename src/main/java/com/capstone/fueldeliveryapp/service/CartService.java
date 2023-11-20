package com.capstone.fueldeliveryapp.service;

import com.capstone.fueldeliveryapp.entity.Cart;
import com.capstone.fueldeliveryapp.entity.Fuel;
import com.capstone.fueldeliveryapp.entity.FuelItem;
import com.capstone.fueldeliveryapp.entity.FuelItemDetails;
import com.capstone.fueldeliveryapp.repository.CartRepository;
import com.capstone.fueldeliveryapp.repository.FuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    FuelRepository fuelRepository;

    // To get cart for a user
    public Cart getCartForUser(String userId){
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        List<FuelItem> fuelsInCart = cart.getFuelsInCart();
        List<FuelItemDetails> fuelDetailsInCart = new ArrayList<>();
        for(FuelItem fuelItem: fuelsInCart){
            String fuelId = fuelItem.getFuelTypeId();
            System.out.println("fuelId "+fuelId);
            Fuel fuel = fuelRepository.findById(fuelId).orElse(null);
            System.out.println(fuel);
            FuelItemDetails fuelWithDetails  = new FuelItemDetails(fuelItem.getFuelItemId(), fuelItem.getFuelTypeId(), (Integer) fuelItem.getFuelQuantity(), fuel, fuelItem.getFuelUnit());
            fuelDetailsInCart.add(fuelWithDetails);
        }
        cart.setFuelDetailsInCart(fuelDetailsInCart);
        cart.setFuelsInCart(null);
        return cart;
    }

    // To add a fuel item to the cart
    public Cart addItemToCart(String userId, FuelItem fuelItem){
        System.out.println(fuelItem);

//        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
//            System.out.println("here");
//            Cart newCart = new Cart();
////            newCart.setCartId(generateCartId());
//            newCart.setUserId(userId);
//            System.out.println("here2");
//            return cartRepository.save(newCart);
//        });

        Optional<Cart> foundCart = cartRepository.findByUserId(userId);
        if(foundCart.isEmpty()){
            System.out.println("when add first item to cart");
            // when add first item to cart
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            List<FuelItem> fuelsInCart = new ArrayList<>();
            // create first item in cart
            if(fuelItem.getFuelUnit() == null) fuelItem.setFuelUnit("Liters");
            fuelsInCart.add(fuelItem);
            newCart.setFuelsInCart(fuelsInCart);
            return cartRepository.save(newCart);
        }
        Cart cart = foundCart.get();
        List<FuelItem> fuelsInCart = cart.getFuelsInCart();
        for(FuelItem item: fuelsInCart){
            if(item.getFuelTypeId().equals(fuelItem.getFuelTypeId())){
                item.setFuelQuantity(item.getFuelQuantity().intValue() + fuelItem.getFuelQuantity().intValue());
                return cartRepository.save(cart);
            }
        }
        System.out.println(fuelItem);
        if(fuelItem.getFuelUnit() == null) fuelItem.setFuelUnit("Liters");
        fuelsInCart.add(fuelItem);
        cart.setFuelsInCart(fuelsInCart);
        return cartRepository.save(cart);
    }

    // To remove a fuel item from the cart
    public Cart removeItemFromCart(String userId, String fuelItemId){
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        List<FuelItem> fuelsInCart = cart.getFuelsInCart();
        List<FuelItem> updatedFuelsInCart = new ArrayList<>();
        for(FuelItem fuelItem: fuelsInCart){
            if(!fuelItem.getFuelItemId().equals(fuelItemId))
                updatedFuelsInCart.add(fuelItem);
        }
        cart.setFuelsInCart(updatedFuelsInCart);
        Cart updatedCart = cartRepository.save(cart);
        List<FuelItem> fuelsInUpdatedCart = updatedCart.getFuelsInCart();
        List<FuelItemDetails> fuelDetailsInUpdatedCart = new ArrayList<>();
        for(FuelItem fuelItem: fuelsInUpdatedCart){
            String fuelId = fuelItem.getFuelTypeId();
            Fuel fuel = fuelRepository.findById(fuelId).orElse(null);
            FuelItemDetails fuelWithDetails  = new FuelItemDetails(fuelItem.getFuelItemId(), fuelItem.getFuelTypeId(), (Integer) fuelItem.getFuelQuantity(), fuel, fuelItem.getFuelUnit());
            fuelDetailsInUpdatedCart.add(fuelWithDetails);
        }
        updatedCart.setFuelDetailsInCart(fuelDetailsInUpdatedCart);
        updatedCart.setFuelsInCart(null);
        return updatedCart;
    }

    // To check out single-item

    // To check out cart

    // To clear cart
    public Cart clearCart(String userId){
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        cart.setFuelsInCart(new ArrayList<>());
        return cartRepository.save(cart);
    }

    // Helper method to generate a unique order ID (using UUID for simplicity)
    private String generateCartId() {
        return UUID.randomUUID().toString();
    }
}
