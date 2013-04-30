package com.washingtonpost.videocomments.web;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//@Controller
public class VideoController {

    private String path;

    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public void video(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
        if (id < 0) {
            throw new IllegalArgumentException("");
        }
        response.setContentType("video/x-flv");
        File file = new File(path + File.separator + id + ".flv");
        if (!file.exists()) {
            throw new RuntimeException("Not found");
        }
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            IOUtils.copy(stream, response.getOutputStream());
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    @RequestMapping(value = "/thumbnail", method = RequestMethod.GET)
    public void thumbnail(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
        if (id < 0) {
            throw new IllegalArgumentException("");
        }
        response.setContentType("image/jpeg");
        File file = new File(path + File.separator + id + ".jpg");
        if (!file.exists()) {
            throw new RuntimeException("Not found");
        }
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            IOUtils.copy(stream, response.getOutputStream());
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }


//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public void serviceExceptionHandler(Exception e, HttpServletResponse response) throws IOException {
//        logger.error("Error", e);
//    }


    public void setPath(String path) {
        this.path = path;
    }
}
