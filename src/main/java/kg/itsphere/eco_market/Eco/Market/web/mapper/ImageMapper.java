package kg.itsphere.eco_market.Eco.Market.web.mapper;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Image;
import kg.itsphere.eco_market.Eco.Market.web.dto.image.ImageResponse;

import java.util.List;

public interface ImageMapper {
    ImageResponse toDto(Image image);
    List<ImageResponse> toDtoS(List<Image> all);
    Image toDtoImage(Image image, String name);
}
