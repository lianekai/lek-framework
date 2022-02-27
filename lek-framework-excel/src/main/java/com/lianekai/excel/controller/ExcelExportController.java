package com.lianekai.excel.controller;

import com.lianekai.excel.pojo.dto.ExcelExportDTO;
import com.lianekai.excel.service.ExcelService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 导出Excel控制层
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/26 22:15
 */
@RestController("lek-framework")
@Slf4j
@Api(value = "导出controller层", tags = "导出controller层")
public class ExcelExportController extends BaseExcelController {
    @Autowired
    ExcelService excelService;

    @PostMapping("generateExcelTemplate")
    public void generateExcelTemplate(HttpServletResponse response, @RequestBody ExcelExportDTO excelExportDTO) throws Exception {
        String excelName= URLEncoder.encode("文档实例批量导入模板", StandardCharsets.UTF_8.name()).replace("\\+", "%20");
        super.setResponseHeadAsExcel(response, excelName);
        excelService.generateExcelTemplate(response,excelExportDTO);
    }
}
