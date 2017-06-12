package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by zhans-pc on 2017/6/12.
 */
public class ExcelView extends AbstractExcelView {
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Sheet sheet= workbook.createSheet("one");
        Row row=sheet.createRow(0);
        row.createCell(0).setCellValue("partner_id");
        row.createCell(1).setCellValue("poi_id");
        row.createCell(2).setCellValue("status");
        row=sheet.createRow(1);
        row.createCell(0).setCellValue(100);
        row.createCell(1).setCellValue(1000);
        row.createCell(2).setCellValue(1);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        //这里对文件名进行编码，保证下载时汉字显示正常
        String fileName = URLEncoder.encode("example.xls", "utf-8");
        //Content-disposition属性设置成以附件方式进行下载
        response.setHeader("Content-disposition", "attachment;filename="
                + fileName);
        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }
}
