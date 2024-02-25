package com.mimka.shoplocbe.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements  ImageService {

    private final AmazonS3 amazonS3;

    @Value("${amazon.s3.bucket-name}")
    private String amazonS3BucketName;

    public ImageServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String uploadImage(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        String imageUrl = "URL://error-while-uploading-image";

        /*
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            this.amazonS3.putObject(this.amazonS3BucketName, fileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            log.warn("An error occurred while attempting to read the file : " + e.getMessage());
        }
        imageUrl = this.amazonS3.getUrl(this.amazonS3BucketName, fileName).toString();
        */
        // TODO : complete commerce.
        return imageUrl;
    }
}
