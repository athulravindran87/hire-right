package com.hright.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Resume implements Serializable {

    private static final long serialVersionUID = -7866318469929556557L;

    private String id;
    private String body;
    private String localFileSystemPath;
    private String resumeUrl;
    private String createDate;

    public static Resume to(File file)
    {
        return Resume.builder()
                .id(generateUniqueId())
                .localFileSystemPath(file.getAbsolutePath())
                .createDate(LocalDate.now(ZoneId.of("Australia/Sydney")).format(DateTimeFormatter.ISO_DATE))
                .build();
    }

    private static String generateUniqueId() {
        return "R-" + LocalDate.now() + "-" + System.nanoTime();
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Unable to convert to resume object to json {}", this, e);
        }
        return "";
    }
}
