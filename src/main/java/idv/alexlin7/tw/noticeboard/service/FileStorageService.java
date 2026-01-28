package idv.alexlin7.tw.noticeboard.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String store(MultipartFile file, String contextPath) throws IOException;
}
