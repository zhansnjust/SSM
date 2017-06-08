package controller;

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
import pojo.User;
import service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
    public String fileUpload(MultipartFile uploadFile, HttpSession session) throws IOException {
        String fileName = uploadFile.getOriginalFilename();
        String leftPath = session.getServletContext().getRealPath("/js");
        File f = new File(leftPath, fileName);
        uploadFile.transferTo(f);
        return "upload ok";
    }

    @RequestMapping("/down")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        byte[] body = null;
        ServletContext servletContext = session.getServletContext();
        InputStream in = session.getServletContext().getResourceAsStream("/js/bootstrap.js");
        body = new byte[in.available()];
        in.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("Context-Disposition", "attachment;filename=bootstrap.js");
        HttpStatus statusConde = HttpStatus.OK;
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusConde);
        return response;
    }
}
