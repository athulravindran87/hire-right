package com.hright.local;

import com.hright.FileService;
import org.apache.commons.io.FileUtils;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
@ConditionalOnProperty(name = "dm.aws.enabled", havingValue = "false", matchIfMissing = false)
public class LocalFileService implements FileService {

    @Override
    public File retrieveFile(String url) {
        return FileUtils.getFile(url);
    }

    @Override
    public String save(File file) {
        return null;
    }

    @Override
    public String saveAll(List<File> files) {
        Lists.adapt(files)
                .reject(Objects::isNull)
                .forEach(this::save);
        return "";
    }
}
