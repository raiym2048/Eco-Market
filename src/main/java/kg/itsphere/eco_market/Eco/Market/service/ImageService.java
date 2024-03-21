package kg.itsphere.eco_market.Eco.Market.service;

import jakarta.transaction.Transactional;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    @Transactional
    Image uploadFile(MultipartFile file, Image oldDocument);

    Image uploadFile(MultipartFile file);

    void uploadFileToS3Bucket(MultipartFile file);

    byte[] downloadFile(String fileName);

    void deleteFile(Long id);

    Image showById(Long id);
}
