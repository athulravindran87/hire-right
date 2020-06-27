package com.hright;

import com.hright.model.Resume;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public interface FileService {

    File retrieveFile(String url);

    void save(Resume resume);

    void saveAll(List<Resume> resumes);
}
