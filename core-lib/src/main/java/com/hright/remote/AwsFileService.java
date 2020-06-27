package com.hright.remote;

import com.hright.FileService;
import com.hright.model.Resume;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@ConditionalOnProperty(name = "dm.aws.enabled", havingValue = "true", matchIfMissing = true)
public class AwsFileService implements FileService {

    @Override
    public File retrieveFile(String url) {
        return null;
    }

    @Override
    public void save(Resume resume) {
    }

    @Override
    public void saveAll(List<Resume> resumes) {
    }
}
