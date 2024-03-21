package kg.itsphere.eco_market.Eco.Market.service;

import com.amazonaws.services.s3.model.S3Object;
import jakarta.transaction.Transactional;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Image;
import kg.itsphere.eco_market.Eco.Market.web.dto.image.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    List<ImageResponse> getAllImagesInfo();
    S3Object getFile(String keyName);
    String uploadFile(MultipartFile file);
    byte[] downloadFile(String fileName);
    String deleteFile(String fileName);
}
