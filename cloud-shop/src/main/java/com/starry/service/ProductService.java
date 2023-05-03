package com.starry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starry.model.ProductDO;
import com.starry.vo.ProductVO;

import java.util.List;

/**
 *
 */
public interface ProductService extends IService<ProductDO> {


    List<ProductVO> productDOList();

    ProductVO findDetailById(long productId);
}
