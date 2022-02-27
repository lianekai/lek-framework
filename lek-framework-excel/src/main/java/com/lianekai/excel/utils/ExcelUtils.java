package com.lianekai.excel.utils;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

/**
 * @author lianekai
 */
public class ExcelUtils {

    private static final CharSequence POINT_ZERO = ".0";

    /**增加下拉值的约束*/
    public static void addSelectConstraint(Sheet sheet, Cell cell, int startRow, int endRow, String[] options) {
        if (sheet instanceof XSSFSheet) {
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                    .createExplicitListConstraint(options);
            CellRangeAddressList addressList = new CellRangeAddressList(startRow, endRow, cell.getColumnIndex(), cell.getColumnIndex());
            XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            sheet.addValidationData(validation);
        } else {
            CellRangeAddressList addressList = new CellRangeAddressList(startRow, endRow, cell.getColumnIndex(), cell.getColumnIndex());
            DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(options);
            DataValidation validation = new HSSFDataValidation(addressList, dvConstraint);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            sheet.addValidationData(validation);
        }
    }

    /**增加大小范围值的约束*/
    public static void addScheduleConstraint(Sheet sheet, Cell cell, long min, long max, int startRow, int endRow, Workbook wb) {
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(startRow, endRow, cell.getColumnIndex(), cell.getColumnIndex());
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createIntegerConstraint(DataValidationConstraint.OperatorType.BETWEEN, String.valueOf(min), String.valueOf(max));
        DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
        /** 输入无效值时是否显示错误框 */
        dataValidation.setShowErrorBox(true);
        /** 验证输入数据是否真确 */
        dataValidation.setSuppressDropDownArrow(true);
        /** 设置无效值时 是否弹出提示框 */
        dataValidation.setShowPromptBox(true);
        /** 设置无效值时的提示框内容 createErrorBox */
        //dataValidation.createPromptBox(TIPS, TIPS_NUMBER_MESSAGE);
        sheet.addValidationData(dataValidation);
        /**如果是数值类型*/
        if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
            if (!String.valueOf(cell.getNumericCellValue()).contains(POINT_ZERO)) {
                //设置数量单元格为红色
                CellStyle style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.RED.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(style);
            }
        }
    }

    /**增加时间的约束*/
    public static void addDateConstraint(Sheet sheet, Cell cell, String startTime, String endTime, int startRow, int endRow, String format) {
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(startRow, endRow, cell.getColumnIndex(), cell.getColumnIndex());
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createDateConstraint(DataValidationConstraint.OperatorType.BETWEEN, startTime, endTime, format);
        DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
        dataValidation.setShowErrorBox(true);
        dataValidation.setSuppressDropDownArrow(true);
        dataValidation.setShowPromptBox(true);
        //dataValidation.createPromptBox(TIPS, TIPS_MESSAGE);这里去掉了，用批注的形式进行只在表头提示 看：CustomCommentHandler类
        sheet.addValidationData(dataValidation);
    }
}
