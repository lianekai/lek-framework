package com.lianekai.excel.controller;

import javax.servlet.http.HttpServletResponse;

/**
 * 基础的Excel处理控制器，在这里对返回的Excel做统一处理作为父类被其他控制器继承
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/26 22:11
 */
public class BaseExcelController {

    /**将返回结果以文件的方式返回*/
    protected  void setResponseHeadAsExcel(HttpServletResponse response, String fileName){
        /**对Excel导出做统一处理*/
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=utf-8''" + fileName);
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
    }
}
