package com.starry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.model.ProductOrderDO;
import com.starry.service.ProductOrderService;
import com.starry.mapper.ProductOrderMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO>
    implements ProductOrderService{

}




