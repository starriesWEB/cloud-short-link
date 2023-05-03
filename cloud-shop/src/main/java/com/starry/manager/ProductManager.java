package com.starry.manager;

import com.starry.model.ProductDO;

import java.util.List;

public interface ProductManager {

    List<ProductDO> list();

    ProductDO findDetailById(long productId);
}