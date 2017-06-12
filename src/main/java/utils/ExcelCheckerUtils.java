package utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import pojo.ExcelChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhans-pc on 2017/6/12.
 */
public class ExcelCheckerUtils {
    public final static String BLANK = "空值";
    public final static String FORMAT_ERROR = "格式不对";

    public static List<ExcelChecker> checkComment(Workbook workbook) {
        List<ExcelChecker> ret = new ArrayList<ExcelChecker>();
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        int rowNum = sheet.getLastRowNum() + 1;
        int columnNum = row.getLastCellNum();
        ExcelChecker excelChecker = null;
        for (int i = 1; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                Cell cell = sheet.getRow(i).getCell(j);
                if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                    excelChecker = new ExcelChecker(i + 1, j + 1, false, BLANK);
                    ret.add(excelChecker);
                } else if (cell.getCellType() != HSSFCell.CELL_TYPE_NUMERIC) {
                    excelChecker = new ExcelChecker(i + 1, j + 1, false, FORMAT_ERROR);
                    ret.add(excelChecker);
                }
            }
        }
        return ret;
    }
}

