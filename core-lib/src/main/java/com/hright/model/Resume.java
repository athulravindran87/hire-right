package com.hright.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resume implements Serializable {

    private static final long serialVersionUID = -7866318469929556557L;

    private String id;
    private String body;
    private String resumeUrl;

    public static Resume to(File file)
    {
        return Resume.builder()
                .id(generateUniqueId())
                .build();
    }

    private static String generateUniqueId()
    {
        return "R-" + LocalDate.now().toString() + "-" + System.currentTimeMillis();
    }
}
