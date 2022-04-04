package com.atguigu.srb.core.service;

import com.alibaba.excel.EasyExcel;
import com.atguigu.srb.core.listener.ExcelDictDTOListener;
import com.atguigu.srb.core.pojo.entity.Dict;
import com.atguigu.srb.core.pojo.entity.dto.ExcelDictDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

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
}
