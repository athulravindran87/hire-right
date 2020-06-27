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

    public static Resume to(File file)
    {
        return Resume.builder()
                .id(generateUniqueId())
                .localFileSystemPath(file.getAbsolutePath() + "/" + file.getName())
                .build();
    }

    private static String generateUniqueId() {
        return "R-" + LocalDate.now().toString() + "-" + System.currentTimeMillis();
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
