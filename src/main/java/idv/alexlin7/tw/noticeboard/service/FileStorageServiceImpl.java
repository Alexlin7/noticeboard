package idv.alexlin7.tw.noticeboard.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springdoc.core.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.uuid.Generators;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "pdf");

    @Value("${upload.storage-dir}")
    private String basePath;

    /**
     * Stores a file, giving it a unique UUIDv7 filename.
     *
     * @param file The file to store.
     * @return The unique filename that was generated.
     * @throws IOException if the file could not be stored.
     */
    @Override
    public String store(MultipartFile file, String contextPath) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }

        String filePath = FilenameUtils.concat(basePath, contextPath);
        Path path = Path.of(filePath);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("無法建立儲存目錄", e);
        }
        // 1. 取得副檔名並進行白名單校驗
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);

        // 簡單的白名單檢查 (可根據需求調整)
        if (extension == null || !allowedExtensions.contains(extension.toLowerCase())) {
            throw new SecurityException("Unsupported file type.");
        }

        // 2. 生成唯一的檔名 (防止覆蓋與預測)
        String filename = Generators.timeBasedEpochGenerator().generate() + Constants.DOT + extension;

        Path targetLocation = path.resolve(filename).normalize();
        if (!targetLocation.startsWith(path)) {
            throw new SecurityException("非法儲存路徑");
        }

        File targetFile = targetLocation.toFile();

        try (InputStream is = file.getInputStream()) {
            FileUtils.copyInputStreamToFile(is, targetFile);
        } catch (IOException e) {
            throw new IOException("Failed to save file to disk", e);
        }

        return filename;


    }
}
