package com.capstone.fueldeliveryapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("cart")
public class Cart {
    @Id
    private String cartId;
    private String userId;
    private List<FuelItem> fuelsInCart;
    private List<FuelItemDetails> fuelDetailsInCart;
    public Cart() {}
    public Cart(String cartId, String userId, List<FuelItem> fuelsInCart) {
        this.cartId = cartId;
        this.userId = userId;
        this.fuelsInCart = fuelsInCart;
    }

    public String getCartId() {
        return cartId;
    }
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<FuelItem> getFuelsInCart() {
        return fuelsInCart;
    }

    public void setFuelsInCart(List<FuelItem> fuelsInCart) {
        this.fuelsInCart = fuelsInCart;
    }

    public void setFuelDetailsInCart(List<FuelItemDetails> fuelDetailsInCart) { this.fuelDetailsInCart = fuelDetailsInCart;}

    public List<FuelItemDetails> getFuelDetailsInCart() {
        return fuelDetailsInCart;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId='" + cartId + '\'' +
                ", userId='" + userId + '\'' +
                ", fuelsInCart=" + fuelsInCart +
                ", fuelDetailsInCart=" + fuelDetailsInCart +
                '}';
    }
}
