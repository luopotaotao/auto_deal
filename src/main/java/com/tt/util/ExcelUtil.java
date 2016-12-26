package com.tt.util;

import org.apache.poi.hssf.usermodel.*;

import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/12/10.
 */
public class ExcelUtil {
    public static HSSFWorkbook createExcel(List<ExcelSheet> sheets){
        HSSFWorkbook wb = new HSSFWorkbook();
        sheets.forEach(item->{
            HSSFSheet sheet = wb.createSheet(item.getName());
            //设置列名
            HSSFRow row = sheet.createRow( 0);
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            List<String> columns = item.getColumns();
            for(int i=0;i<columns.size();i++){
                HSSFCell cell = row.createCell( i);
                cell.setCellValue(columns.get(i));
                cell.setCellStyle(style);
            }
            //设置数据
            List<Object[]> data = item.getData();
            for(int i=0;i<data.size();i++){
                Object[] data_row = data.get(i);
                HSSFRow a_row = sheet.createRow(i+1);
                for(int j=0;j<data_row.length;j++){
                    HSSFCell cell = a_row.createCell( j);
                    Object val = data_row[j];
                    if(val instanceof Double){
                        cell.setCellValue((Double)val);
                    }else if(val instanceof String){
                        cell.setCellValue((String) val);
                    }else if(val instanceof Boolean){
                        cell.setCellValue((Boolean) val);
                    }
                }
            }
        });
        return wb;
    }
}


