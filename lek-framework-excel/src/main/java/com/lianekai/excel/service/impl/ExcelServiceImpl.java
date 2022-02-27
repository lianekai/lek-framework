package com.lianekai.excel.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.google.common.collect.Lists;
import com.lianekai.excel.handler.CustomCellConstraintHandler;
import com.lianekai.excel.handler.CustomColumnWidthHandler;
import com.lianekai.excel.pojo.dto.ExcelExportDTO;
import com.lianekai.excel.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Excel Service实现
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/26 22:19
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public void generateExcelTemplate(HttpServletResponse response, ExcelExportDTO excelExportDTO) throws Exception {
        List<String> excelHeaders=excelExportDTO.getExcelHeaders();
        if(CollectionUtils.isEmpty(excelHeaders)){
            throw new Exception("生成的表头不能未空");
        }

        ExcelWriter writer = EasyExcelFactory
                .write(response.getOutputStream())
                .registerWriteHandler(new CustomColumnWidthHandler())
                .registerWriteHandler(new CustomCellConstraintHandler(""))
                .inMemory(true)
                .build();

        /**动态添加表头，适用一些表头动态变化的场景*/
        WriteSheet sheet1 = new WriteSheet();
        sheet1.setSheetNo(0);

        /**创建一个表格，用于 Sheet 中使用*/
        WriteTable table = new WriteTable();
        table.setTableNo(1);

        List<List<String>> header = Lists.newArrayList();
        for (String field : excelHeaders) {
            header.add(Lists.newArrayList(field));
        }
        table.setHead(header);

        sheet1.setSheetName("学生信息表");
        writer.write(Lists.newArrayList(), sheet1, table);
        writer.finish();
    }
}
