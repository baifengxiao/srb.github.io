package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.Dict;
import com.atguigu.srb.core.pojo.entity.dto.ExcelDictDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author yupengtao
 * @since 2022-03-30
 */
public interface DictService extends IService<Dict> {

    public void importData(InputStream inputStream);


    List<ExcelDictDTO> listDictData();

    List<Dict> listByParentId(Long parentId);

    List<Dict> findByDictCode(String dictCode);
    String  getNameByParentDictCodeAndValue(String dictCode, Integer value);
}
