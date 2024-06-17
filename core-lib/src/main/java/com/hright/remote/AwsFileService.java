package com.hright.remote;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hright.FileService;
import com.hright.model.Resume;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@ConditionalOnProperty(name = "dm.aws.enabled", havingValue = "true", matchIfMissing = true)
public class AwsFileService implements FileService {

    @Autowired
    private AmazonS3Client amazonS3;

    @Autowired
    private AwsConfig awsConfig;

    @Override
    public File retrieveFile(String url) {
        return new File(url);
    }

    @Override
    public void save(Resume resume) {
        File file = new File(resume.getLocalFileSystemPath());
        log.info("Writing file: {} to s3 bucket: {}", file.getName(), this.awsConfig.getBucketName());
        this.amazonS3.putObject(new PutObjectRequest(this.awsConfig.getBucketName(), file.getName(), file));
        URL s3Url = this.amazonS3.getUrl(this.awsConfig.getBucketName(), file.getName());
        log.info("S3 url is " + s3Url.toExternalForm());
        resume.setResumeUrl(s3Url.toExternalForm());
    }


    @Override
    public void saveAll(List<Resume> resumes) {
        Lists.adapt(resumes).reject(Objects::isNull).forEach(this::save);
    }
}
