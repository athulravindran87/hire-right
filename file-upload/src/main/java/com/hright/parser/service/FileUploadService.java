package com.hright.parser.service;

import com.hright.parser.messaging.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class FileUploadService {

    @Value("${file.storage.location}")
    private String tempFolder;

    @Autowired
    private MessageSender messageSender;

    public MutableList<File> process(MultipartFile file) throws Exception {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        MutableList<File> files = Lists.mutable.empty();

        File subdirectory = this.processOriginal(file);
        if (org.apache.commons.lang3.StringUtils.endsWith(fileName, ".zip")) {
            files = this.extractFilesFromZip(subdirectory.getAbsolutePath(), file.getOriginalFilename());
        }

        if (org.apache.commons.lang3.StringUtils.endsWithAny(fileName, ".pdf", ".PDF")) {
            files.add(FileUtils.getFile(subdirectory, file.getOriginalFilename()));
        }

        this.messageSender.sendMessage(files);
        return files;
    }


    protected File processOriginal(@RequestParam("file") MultipartFile file) throws IOException {
        String subDirName = LocalDate.now().toString().concat("-" + System.currentTimeMillis());
        File subdirectory = new File(tempFolder, subDirName);
        this.saveOriginal(subdirectory.getAbsolutePath(), file);
        return subdirectory;
    }

    protected MutableList<File> extractFilesFromZip(String folder, String fileName) {
        File file = FileUtils.getFile(folder, fileName);
        File subdirectory = new File(folder, "extract");
        MutableList<File> files = Lists.mutable.empty();
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
            this.createSubDir(subdirectory);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            File destDir = new File(subdirectory.getAbsolutePath());
            byte[] buffer = new byte[1024];
            while (zipEntry != null) {
                log.info("Unzipping File: {}", zipEntry.getName());
                if (zipEntry.getName().contains("__MACOSX")) {
                    zipEntry = zipInputStream.getNextEntry();
                    continue;
                }

                File newFile = this.newFile(destDir, zipEntry);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zipEntry = zipInputStream.getNextEntry();
                files.add(newFile);
            }
            zipInputStream.closeEntry();
        } catch (IOException e) {
            log.error("Exception While Extracting ZipFile: {}", file.getName(), e);
        }
        log.info("Total Files unzipped: {}", files.size());
        return files;
    }

    private void createSubDir(File subdirectory) throws IOException {
        org.apache.tomcat.util.http.fileupload.FileUtils.forceMkdir(subdirectory);
    }

    private void saveOriginal(String tempFolder, MultipartFile file) throws IOException {
        this.createSubDir(new File(tempFolder));
        byte[] bytes = file.getBytes();
        Path path = Paths.get(tempFolder + "/" + file.getOriginalFilename());
        Files.write(path, bytes);
        log.info("Saved multipart file: {} to temp location: {}", file.getOriginalFilename(), tempFolder);
    }

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

}
