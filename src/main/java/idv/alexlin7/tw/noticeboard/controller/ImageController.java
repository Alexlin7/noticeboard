package idv.alexlin7.tw.noticeboard.controller;

import idv.alexlin7.tw.noticeboard.service.FileStorageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final FileStorageService fileStorageService;

    public ImageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String filename = fileStorageService.store(file, "notice_images");

            // Construct the file download URI
            String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/notice_images/")
                    .path(filename)
                    .toUriString();

            return ResponseEntity.ok(Map.of("url", fileUri));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not upload the file: " + e.getMessage());
        }
    }
}
