package service;

import domain.Review;
import util.LoggerUtil;

import static util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        LoggerUtil.log("retrieveReviews after Delay");
        return new Review(200, 4.5);
    }
}
