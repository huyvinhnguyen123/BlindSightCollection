package Blind.Sight.Commnunity.domain.service;

import Blind.Sight.Commnunity.domain.entity.Image;
import Blind.Sight.Commnunity.domain.repository.ImageRepository;
import Blind.Sight.Commnunity.domain.service.custom.GoogleDriveService;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final GoogleDriveService googleDriveService;
    @Value("${drive.folder.category}")
    private String categoryPath;
    @Value("${drive.folder.product}")
    private String productPath;

    public Image findImageById(String imageId) {
        Image existImage = imageRepository.findById(imageId).orElseThrow(
                () -> {
                    log.error("Not found this image: {}", imageId);
                    return new NullPointerException("Not found this image: " + imageId);
                }
        );

        log.info("Found image");
        return existImage;
    }

    public void createImage(MultipartFile file, String path) throws GeneralSecurityException, IOException {
        File fileDrive = googleDriveService.uploadToDrive(file, path);
        Image image = new Image();
        image.setImagePathId(fileDrive.getId());
        image.setImageName(file.getOriginalFilename());
        image.setImagePath(fileDrive.getWebContentLink());
        imageRepository.save(image);

        log.info("create image success!");
    }

    public void updateImage(String imageId, MultipartFile file, String path) throws GeneralSecurityException, IOException {
        Image existImage = findImageById(imageId);
        File fileDrive = googleDriveService.uploadToDrive(file, path);
        existImage.setImagePathId(fileDrive.getId());
        existImage.setImageName(file.getOriginalFilename());
        existImage.setImagePath(fileDrive.getWebContentLink());
        imageRepository.save(existImage);

        log.info("update image success!");
    }

    public void deleteImage(String imageId) throws GeneralSecurityException, IOException {
        Image existImage = findImageById(imageId);
        googleDriveService.deleteFileFromDrive(existImage.getImagePathId());
        imageRepository.delete(existImage);

        log.info("Delete image success");
    }
}
