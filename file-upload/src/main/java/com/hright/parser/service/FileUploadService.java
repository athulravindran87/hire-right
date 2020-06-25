package com.hright.parser.service;

import com.hright.model.Resume;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
public class FileUploadService {

    public void process(List<File> files) {
        MutableList<Resume> resumes = Lists.adapt(files)
                .reject(Objects::isNull)
                .collect(Resume::to);


    }

}
