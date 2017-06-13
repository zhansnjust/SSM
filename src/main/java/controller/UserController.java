package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import mapper.UserMapper;
import mapper.WhiteMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import pojo.ExcelChecker;
import pojo.MtaHotelWhiteList;
import pojo.User;
import service.UserService;
import utils.ExcelCheckerUtils;
import utils.ExcelView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by zhans-pc on 2017/5/4.
 */
@Controller
public class UserController {

    public static String result;
    @Autowired
    UserService userService;

    @Resource
    WhiteMapper whiteMapper;

    @RequestMapping("/findAll")
    public String findAll(ModelMap model, Map<String, Object> map) throws Exception {
        List<User> list = userService.findUser();
        model.addAttribute("key", "value");
        map.put("names", Arrays.asList("nihao", "hah"));
        return "find";
    }

    @RequestMapping("/findOne")
    public String findById(@RequestParam("id") String id, Model model) {
        System.out.println(id);
        Map<String, User> u = userService.findById(id);
        System.out.println(u.keySet());
        System.out.println("---");
        System.out.println(u.values());
        model.addAttribute("user", u);
        return "find";
    }

    @ResponseBody
    @RequestMapping("/testResponse")
    public String testResponseBody() {
        return "test reo";
    }

    @RequestMapping("/springUpload")
    @ResponseBody
    public String springUpload(HttpServletRequest request) throws IllegalStateException, IOException {
        System.out.println("-----");

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    String path = "~/devOps" + file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }

            }

        }
        return "/success";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String fileUpload(MultipartFile uploadFile, HttpServletResponse response) throws IOException {
        String filename = uploadFile.getOriginalFilename();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if (!filename.endsWith(".xls")) {
            return "请输入excel -2003及以下版本的文件";
        }
        Workbook workbook = new HSSFWorkbook(uploadFile.getInputStream());
        String ret = "";
        List<ExcelChecker> list = ExcelCheckerUtils.checkComment(workbook);
        if (list == null || list.size() == 0) {
            List<MtaHotelWhiteList> batchInsert = new ArrayList<MtaHotelWhiteList>();
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);
            int rowNum = sheet.getLastRowNum() + 1;
            int columnNum = row.getLastCellNum();
            for (int i = 1; i < rowNum; i++) {
                MtaHotelWhiteList mtaHotelWhiteList = new MtaHotelWhiteList();
                mtaHotelWhiteList.setGmtCreate(new Date());
                mtaHotelWhiteList.setGmtModified(new Date());
                Row tmp = sheet.getRow(i);
                Double partnerId = tmp.getCell(0).getNumericCellValue();
                Double poiId = tmp.getCell(1).getNumericCellValue();
                Double status = tmp.getCell(2).getNumericCellValue();
                mtaHotelWhiteList.setPartnerId(partnerId.longValue());
                mtaHotelWhiteList.setPoiId(poiId.longValue());
                mtaHotelWhiteList.setStatus(status.intValue());
                batchInsert.add(mtaHotelWhiteList);
            }
            whiteMapper.batchInsert(batchInsert);
            Map<String, String> map = new HashMap<String, String>();
            map.put("msg", "success");
            ret = JSON.toJSONString(map);
            workbook.close();
            return ret;
        } else {
            ret = JSON.toJSONString(list);
            workbook.close();
            return ret;
        }


    }

    @RequestMapping("/download")
    public ModelAndView downloanExcel() {
        ExcelView excelView = new ExcelView();
        return new ModelAndView(excelView);
    }
}
