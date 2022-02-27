package com.lianekai.excel.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

/**
 * cell样式自定义拦截处理
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/25 22:12
 */
public class CustomCellStyleHandler extends AbstractCellStyleStrategy {

    private CellStyle headCellStyle;

    private Workbook workBook;

    public CustomCellStyleHandler(String bid) {
        /**通过一些构造方式业务*/
    }
    @Override
    protected void initCellStyle(Workbook workbook) {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //设置头字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 13);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headCellStyle = StyleUtil.buildHeadCellStyle(workbook, headWriteCellStyle);
        this.workBook = workbook;
    }

    /**设置表头的样式*/
    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        cell.setCellStyle(headCellStyle);
    }
    /**设置内容的样式 包括颜色、字体、对齐方式等*/
    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        /**拿到单行表头的名字*/
        String header = head.getHeadNameList().get(0);

        CellStyle cellStyle = workBook.createCellStyle();
        /**默认居中*/
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        /**
         * 表头是姓名居左
         * */
        if ("姓名".equals(header)) {
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            cell.setCellStyle(cellStyle);
        }
        /**
         * 表头是性别居中
         * */
        if ("性别".equals(header)) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(cellStyle);
        }
        /**
         * 表头是年龄居右
         * */
        if ("年龄".equals(header)) {
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
            cell.setCellStyle(cellStyle);
        }
    }
}
