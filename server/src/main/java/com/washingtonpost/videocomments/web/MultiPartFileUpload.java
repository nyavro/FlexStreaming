package com.washingtonpost.videocomments.web;


import org.springframework.web.multipart.MultipartFile;

public class MultiPartFileUpload {

    private Long id;

    private MultipartFile file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }
}
