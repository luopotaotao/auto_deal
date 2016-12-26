package com.tt.web.controller.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.tt.web.controller.BaseController;
import com.tt.web.exception.NotFoundException;
import com.tt.ext.web.FileUploadProgressListener;
import com.tt.model.File;
import com.tt.service.FileService;
import com.tt.util.FileUtil;
import com.tt.util.SessionUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by tt on 2016/11/28.
 */
@Controller
@RequestMapping("file")
public class FileManageController extends BaseController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file, @RequestParam(name = "resumableFilename") String resumableFilename) {
        System.out.println(resumableFilename);
        String filePath = SessionUtil.get().getServletContext().getRealPath("/") + "WEB-INF/resources/upload/";
        UUID id = null;
        try {
            id = FileUtil.saveFile(file, filePath, uuid -> {
                fileService.add(new File(uuid.toString(), resumableFilename));
                System.out.println(uuid.toString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        SessionUtil.removeAttribute(FileUploadProgressListener.SESSION_PROGRESS_KEY);
        Map<String, Object> ret = new HashMap<>();
        ret.put("uuid", id);
        ret.put("fileName", resumableFilename);
        return ret;
    }

    @RequestMapping(value = "/get/{uuid}", method = RequestMethod.GET)
    public File get(@PathVariable String uuid) {
        return fileService.get(uuid);
    }

    @RequestMapping(value = "/download/{uuid}", method = RequestMethod.GET)
    public void download(HttpServletResponse response, @PathVariable("uuid") String uuid) throws IOException {
        File file = fileService.getById(uuid);
        if (file != null) {
            String filePath = SessionUtil.get().getServletContext().getRealPath("/") + "WEB-INF/resources/upload/";
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());

            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            java.io.File f = new java.io.File(filePath + file.getUuid());
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("afttachment; filename=\"%s\"", file.getName()));
            response.setContentLength((int) f.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(f));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } else {
            throw new NotFoundException();
        }
    }
}
