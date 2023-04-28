package com.starry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starry.model.DomainDO;
import com.starry.vo.DomainVO;

import java.util.List;

/**
 *
 */
public interface DomainService extends IService<DomainDO> {

    /**
     * 列举全部可用域名
     * @return
     */
    List<DomainVO> listAll();
}
