package com.starry.manager.impl;

import com.starry.manager.ProductManager;
import com.starry.mapper.ProductMapper;
import com.starry.model.ProductDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductManagerImpl implements ProductManager {

    private final ProductMapper productMapper;


    @Override
    public List<ProductDO> list() {
        return productMapper.selectList(null);
    }

    @Override
    public ProductDO findDetailById(long productId) {
        return productMapper.selectById(productId);
    }
}