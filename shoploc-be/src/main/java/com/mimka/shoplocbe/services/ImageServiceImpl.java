package com.mimka.shoplocbe.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements  ImageService {

    private final AmazonS3 amazonS3;

    @Value("${amazon.s3.bucket-name}")
    private String amazonS3BucketName;

    public ImageServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        this.amazonS3.putObject(this.amazonS3BucketName, fileName, file.getInputStream(), metadata);

        return this.amazonS3.getUrl(this.amazonS3BucketName, fileName).toString();
    }
}
