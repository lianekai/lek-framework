package com.lianekai.excel.handler;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

/**
 * TODO
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/26 15:00
 */
public class CustomColumnWidthHandler extends AbstractColumnWidthStyleStrategy {
    private static final int MAX_COLUMN_WIDTH = 255;

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        /**是表头设置列宽*/
        if (isHead) {
            int columnWidth = cell.getStringCellValue().getBytes().length;
            if (columnWidth > MAX_COLUMN_WIDTH) {
                columnWidth = MAX_COLUMN_WIDTH;
            } else {
                if (cell.getColumnIndex() == 1) {
                    columnWidth = columnWidth + 10;
                } else {
                    columnWidth = columnWidth + 3;
                }

            }
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
        }
    }
}
