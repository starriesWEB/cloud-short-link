package com.starry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.manager.ProductManager;
import com.starry.mapper.ProductMapper;
import com.starry.model.ProductDO;
import com.starry.service.ProductService;
import com.starry.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO>
    implements ProductService{


    private final ProductManager productManager;

    @Override
    public List<ProductVO> productDOList() {
        List<ProductDO> list = productManager.list();
        return list.stream().map(this::beanProcess).collect(Collectors.toList());
    }

    @Override
    public ProductVO findDetailById(long productId) {
        ProductDO productDO = productManager.findDetailById(productId);
        return beanProcess(productDO);
    }


    private ProductVO beanProcess(ProductDO productDO) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(productDO, productVO);
        return productVO;
    }
}




