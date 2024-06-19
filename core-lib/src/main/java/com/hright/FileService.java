package com.hright;

import com.hright.model.Resume;

import java.io.File;
import java.util.List;

public interface FileService {

    File retrieveFile(String url);

    void save(Resume resume);

    void saveAll(List<Resume> resumes);
}
