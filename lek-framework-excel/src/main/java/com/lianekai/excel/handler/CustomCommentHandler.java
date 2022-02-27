package com.lianekai.excel.handler;

import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.util.Iterator;

/**
 * 内容批注约束
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/26 15:27
 */
public class CustomCommentHandler extends AbstractRowWriteHandler {
    private static final String TIPS = "温馨提示:";

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        if(isHead){
            Sheet sheet = writeSheetHolder.getSheet();
            Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();

            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String head = cell.getStringCellValue();
                /**表头是分数 增加批注*/
                if ("score".equals(head)) {
                    generateComment(sheet, drawingPatriarch, TIPS + "TIPS_SERIAL_NUMBER", cell.getColumnIndex());
                }
                /**表头是性别 增加批注*/
                if ("性别".equals(head)) {
                    generateComment(sheet, drawingPatriarch, TIPS + "TIPS_NUMBER_MESSAGE", cell.getColumnIndex());
                }
                /**表头是名字 增加批注*/
                if ("name".equals(head)) {
                    generateComment(sheet, drawingPatriarch, TIPS + "TIPS_TIME_MESSAGE", cell.getColumnIndex());
                }
            }
        }
    }

    private void generateComment(Sheet sheet, Drawing<?> drawingPatriarch, String message, int cellIndex) {
        /** 在对应行数，对应列创建一个批注**/
        Comment comment = drawingPatriarch.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) cellIndex, 0, (short) cellIndex + 1, 1));
        /** 输入批注信息 **/
        comment.setString(new XSSFRichTextString(message));
        /**将批注添加到单元格对象中 从0开始计算 第1行第2列 **/
        sheet.getRow(0).getCell(cellIndex).setCellComment(comment);
    }
}
