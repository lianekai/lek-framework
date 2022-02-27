package com.lianekai.excel.service;

import com.lianekai.excel.pojo.dto.ExcelExportDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/26 22:18
 */
public interface ExcelService {

    /**
     *
     * @param response
     * @return void
     */
    void generateExcelTemplate(HttpServletResponse response, ExcelExportDTO excelExportDTO) throws Exception;
}
