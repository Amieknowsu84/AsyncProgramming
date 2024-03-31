package service;

import domain.ProductInfo;
import domain.ProductOption;
import util.LoggerUtil;

import java.util.List;

import static util.CommonUtil.delay;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String productId) {
        delay(1000);
        List<ProductOption> productOptions = List.of(new ProductOption(1, "64GB", "Black", 699.99),
                new ProductOption(2, "128GB", "Black", 749.99));
        LoggerUtil.log("retrieveProductInfo after Delay");
        return ProductInfo.builder().productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
