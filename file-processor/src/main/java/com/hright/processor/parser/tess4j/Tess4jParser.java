package com.hright.processor.parser.tess4j;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class Tess4jParser {

    @Value("${tesseract.datapath}")
    private String dataPath;

    public String parse(File file) {
        Tesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath(dataPath);
            return tesseract.doOCR(file);
        } catch (TesseractException e) {
            log.error("Unable to parse file: {}", file.getName(), e);
            return "";
        }
    }
}

