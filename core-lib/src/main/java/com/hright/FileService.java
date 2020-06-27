package com.hright;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public interface FileService {

    File retrieveFile(String url);

    String save(File file);

    String saveAll(List<File> files);
}
