package com.atguigu.srb.core.mapper;

import com.atguigu.srb.core.pojo.entity.Dict;
import com.atguigu.srb.core.pojo.entity.dto.ExcelDictDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author yupengtao
 * @since 2022-03-30
 */
public interface DictMapper extends BaseMapper<Dict> {

    void insertBatch(List<ExcelDictDTO> list);
}
