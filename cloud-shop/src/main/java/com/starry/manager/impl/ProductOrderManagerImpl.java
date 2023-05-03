package com.starry.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starry.manager.ProductOrderManager;
import com.starry.mapper.ProductOrderMapper;
import com.starry.model.ProductOrderDO;
import com.starry.vo.ProductOrderVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductOrderManagerImpl implements ProductOrderManager {

    private final ProductOrderMapper productOrderMapper;

    @Override
    public int add(ProductOrderDO productOrderDO) {
        return productOrderMapper.insert(productOrderDO);
    }

    @Override
    public ProductOrderDO findByOutTradeNoAndAccountNo(String outTradeNo, Long accountNo) {
        return productOrderMapper.selectOne(
                Wrappers.lambdaQuery(ProductOrderDO.class)
                        .eq(ProductOrderDO::getOutTradeNo, outTradeNo)
                        .eq(ProductOrderDO::getAccountNo, accountNo)
                        .eq(ProductOrderDO::getDel, 0)
        );
    }

    @Override
    public int updateOrderPayState(String outTradeNo, Long accountNo, String newState, String oldState) {
        return productOrderMapper.update(null,
                Wrappers.lambdaUpdate(ProductOrderDO.class)
                        .eq(ProductOrderDO::getOutTradeNo, outTradeNo)
                        .eq(ProductOrderDO::getAccountNo, accountNo)
                        .eq(ProductOrderDO::getDel, 0)
                        .eq(ProductOrderDO::getState, oldState)
                        .set(ProductOrderDO::getState, newState)
        );
    }


    @Override
    public Map<String, Object> page(int page, int size, Long accountNo, String state) {
        Page<ProductOrderDO> pageInfo = new Page<>(page, size);

        IPage<ProductOrderDO> orderDOIPage;

        orderDOIPage = productOrderMapper.selectPage(pageInfo,
                Wrappers.lambdaQuery(ProductOrderDO.class)
                        .eq(ProductOrderDO::getAccountNo, accountNo)
                        .eq(ProductOrderDO::getDel, 0)
                        .eq(StringUtils.isNotBlank(state), ProductOrderDO::getState, state)
        );

        List<ProductOrderDO> orderDOIPageRecords = orderDOIPage.getRecords();

        List<ProductOrderVO> productOrderVOList = orderDOIPageRecords.stream().map(obj -> {
            ProductOrderVO productOrderVO = new ProductOrderVO();
            BeanUtils.copyProperties(obj, productOrderVO);
            return productOrderVO;
        }).collect(Collectors.toList());

        Map<String, Object> pageMap = new HashMap<>(3);
        pageMap.put("total_record", orderDOIPage.getTotal());
        pageMap.put("total_page", orderDOIPage.getPages());
        pageMap.put("current_data", productOrderVOList);

        return pageMap;
    }


    @Override
    public int del(Long productOrderId, Long accountNo) {
        return productOrderMapper.update(null,
                Wrappers.lambdaUpdate(ProductOrderDO.class)
                        .eq(ProductOrderDO::getAccountNo, accountNo)
                        .eq(ProductOrderDO::getId, productOrderId)
                        .set(ProductOrderDO::getDel, 1)
        );
    }


}