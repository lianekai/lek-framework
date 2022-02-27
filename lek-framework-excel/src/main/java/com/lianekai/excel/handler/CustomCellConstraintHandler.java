package com.lianekai.excel.handler;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.lianekai.excel.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * 自定义列约束拦截处理类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/26 15:08
 */
public class CustomCellConstraintHandler implements CellWriteHandler {
    public CustomCellConstraintHandler(String bid){
        /**需要的处理的业务数据提前查好*/
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        Sheet sheet = writeSheetHolder.getSheet();
        /**表头值*/
        String headValue = head.getHeadNameList().get(0);

        /**约束的设置开始行数与结束行数*/
        int startRow = 1;
        int endRow = 9999;

        /**时间约束的取值范围*/
        String startTime = "Date(1900, 1, 1)";
        String endTime = "Date(2099, 12, 31)";
        String dateFormat = "yyyy/MM/dd";

        /**性别增加下拉约束*/
        if("sex".equals(headValue)){
            String[] options={"男","女","未知"};
            ExcelUtils.addSelectConstraint(sheet, cell, startRow, endRow, options);
        }

        /**表头是生日（时间类型）*/
        if ("birth".equals(headValue)) {
            ExcelUtils.addDateConstraint(sheet, cell, startTime, endTime, startRow, endRow, dateFormat);
        }

        /**表头是分数（时间类型）*/
        if ("score".equals(headValue)) {
            /**数值类型的最大值与最小值*/
            int min = 0;
            int max = 100;
            ExcelUtils.addScheduleConstraint(sheet, cell, min, max, startRow, endRow, sheet.getWorkbook());
        }
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }
}
