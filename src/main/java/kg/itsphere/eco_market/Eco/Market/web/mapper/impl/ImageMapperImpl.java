package kg.itsphere.eco_market.Eco.Market.web.mapper.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Image;
import kg.itsphere.eco_market.Eco.Market.web.dto.image.ImageResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.ImageMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImageMapperImpl implements ImageMapper {
    @Override
    public ImageResponse toDto(Image image) {
        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setFileName(image.getName());
        imageResponse.setPath(image.getPath());
        return imageResponse;
    }

    @Override
    public List<ImageResponse> toDtoS(List<Image> all) {
        List<ImageResponse> imageResponses = new ArrayList<>();
        for(Image image : all) {
            imageResponses.add(toDto(image));
        }
        return imageResponses;
    }

    @Override
    public Image toDtoImage(Image image, String name) {
        image.setName(name);
        image.setPath("localhost:5050/image/view/" + name);
        return image;
    }
}
