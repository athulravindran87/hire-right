package com.hright.parser.controller;

import com.hright.parser.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@Slf4j
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(final FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> parse(@RequestParam("file") MultipartFile file) {
        MutableList<File> files = Lists.mutable.empty();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (!org.apache.commons.lang3.StringUtils.containsAny(fileName, ".zip", ".pdf", ".PDF", ".doc", ".docx", ".DOC", ".DOCX")) {
            return new ResponseEntity<>("Only zip, pdf and doc format files are accepted", HttpStatus.BAD_REQUEST);
        }

        try {
            files = this.fileUploadService.process(file);
        } catch (Exception e) {
            log.error("Unable to parse multipart file: {}", file.getOriginalFilename(), e);
            return new ResponseEntity<>("Unable to parse multipart file " + file.getOriginalFilename(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("File Upload successful for total of " + files.size() + " file(s)", HttpStatus.OK);
    }
}
