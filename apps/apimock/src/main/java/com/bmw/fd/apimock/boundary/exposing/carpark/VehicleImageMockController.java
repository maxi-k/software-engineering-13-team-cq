package com.bmw.fd.apimock.boundary.exposing.carpark;

import com.bmw.fd.apimock.boundary.util.RestMock;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

@RestController
@org.springframework.web.bind.annotation.CrossOrigin
public class VehicleImageMockController {


    private final RestMock images = RestMock.create("data/vehicles/img")
            .build();

    @ResponseBody
    @GetMapping(value = "/img/vehicles/{imageId}", produces = MediaType.IMAGE_PNG_VALUE)
    public void getImage(@PathVariable("imageId") String imageId, HttpServletResponse response) throws IOException {
        if (imageId.startsWith("cccccccc-0000-eeee-9999-")) {
            long seed = Long.parseLong(imageId.substring(imageId.length() - 12));
            BufferedImage img = colorImage(ImageIO.read(new File("data/vehicles/img/vehicle-image-template.png")), seed);
            ImageIO.write(img, "png", response.getOutputStream());
        }

        images.deliverFile(imageId + ".png", response);
    }


    private static BufferedImage colorImage(BufferedImage image, long seed) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        Random random = new Random(seed);

        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                int grayness = (pixels[0] + pixels[1] + pixels[2]) / 3;
                pixels[0] = r * grayness / 255;
                pixels[1] = g * grayness / 255;
                pixels[2] = b * grayness / 255;
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }
}
