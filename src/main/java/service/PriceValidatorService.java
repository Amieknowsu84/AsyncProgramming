package service;


import domain.checkout.CartItem;

import static util.CommonUtil.delay;
import static util.LoggerUtil.log;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem) {
        int cartId = cartItem.getItemId();
        log("isCartItemInvalid : " + cartItem);
        delay(500);
        if (cartId == 7 || cartId == 9 || cartId == 11) {
            return true;
        }
        return false;
    }
}
