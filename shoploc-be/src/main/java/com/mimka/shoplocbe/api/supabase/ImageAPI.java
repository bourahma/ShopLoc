package com.mimka.shoplocbe.api.supabase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class ImageAPI {

    private final RestTemplate restTemplate;

    @Value("${bucket.ref}")
    private String bucketRef;

    @Value("${bucket.name}")
    private String bucketName;

    @Value("${bucket.key}")
    private String bucketKey;

    @Value("${bucket.jwt.token}")
    private String bucketJwtToken;

    @Autowired
    public ImageAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String uploadImage (MultipartFile multipartFile) {
        String imageUrl = "URL://error-while-uploading-image";

        if (multipartFile == null) {
            return imageUrl;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.set("apikey", bucketJwtToken);
        headers.set("Authorization", "Bearer " + bucketKey);

        String imageTitle = UUID.randomUUID().toString();

        try {
            HttpEntity<ByteArrayResource> requestEntity = new HttpEntity<>(new ByteArrayResource(multipartFile.getBytes()), headers);
            String apiUrl = String.format("https://%s.supabase.co/storage/v1/object/%s/%s", bucketRef, bucketName, imageTitle);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.warn("Image uploaded Successfully to supabase.");
                imageUrl = "https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/" + imageTitle;
            } else {
                log.warn("Failed to upload image to supabase.");
            }
        } catch (IOException e) {
            log.warn("Failed to upload image to supabase.");
        }
        return imageUrl;
    }
}
