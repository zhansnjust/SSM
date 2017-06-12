package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import pojo.ExcelChecker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zhans-pc on 2017/6/12.
 */
public class ReadExcel {
    public static void main(String[] args){
        InputStream  is=ReadExcel.class.getResourceAsStream("/example.xls");
        try {
            Workbook workbook=new HSSFWorkbook(is);
            List<ExcelChecker> list=ExcelCheckerUtils.checkComment(workbook);
            System.out.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
