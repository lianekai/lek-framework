package com.lianekai.excel.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * TODO
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/26 22:33
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ExcelExportDTO {

    private List<String> excelHeaders;
}
