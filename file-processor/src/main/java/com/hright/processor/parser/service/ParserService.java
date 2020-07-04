package com.hright.processor.parser.service;

import com.hright.FileService;
import com.hright.model.Resume;
import com.hright.processor.parser.tess4j.Tess4jParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class ParserService {

    private final Tess4jParser parser;
    private final FileService fileService;

    @Autowired
    public ParserService(final Tess4jParser tess4jParser, final FileService fileService) {
        this.parser = tess4jParser;
        this.fileService = fileService;
    }

    public void parse(Resume resume) {
        String body = this.parser.parse(this.retrieveFile(resume.getLocalFileSystemPath()));
        resume.setBody(body);
    }

    private File retrieveFile(String url) {
        return this.fileService.retrieveFile(url);
    }
}
