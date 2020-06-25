package com.hright.processor.parser.controller;

import com.hright.model.Resume;
import com.hright.processor.parser.service.ParserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ParserController {

    private final ParserService service;

    @Autowired
    public ParserController(final ParserService service) {
        this.service = service;
    }

    @PostMapping(value = "/parse", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> parse(@RequestBody Resume resume) {
        if (!this.isValid(resume)) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }

        this.service.parse(resume);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    private boolean isValid(Resume resume) {
        return Objects.nonNull(resume) && StringUtils.isNoneBlank(resume.getId(), resume.getResumeUrl());
    }
}
