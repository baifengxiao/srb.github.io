package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.BorrowerAttach;
import com.atguigu.srb.core.pojo.vo.BorrowerAttachVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 借款人上传资源表 服务类
 * </p>
 *
 * @author yupengtao
 * @since 2022-03-30
 */
public interface BorrowerAttachService extends IService<BorrowerAttach> {
    List<BorrowerAttachVO> selectBorrowerAttchVOList(Long borrowerId);

}
