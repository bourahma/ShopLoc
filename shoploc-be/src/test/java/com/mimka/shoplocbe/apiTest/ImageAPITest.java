package com.mimka.shoplocbe.apiTest;

import com.mimka.shoplocbe.api.supabase.ImageAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class ImageAPITest {

    @Mock
    private RestTemplate mockRestTemplate;

    private ImageAPI imageAPI;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        imageAPI = new ImageAPI(mockRestTemplate);
    }

    @Test
    public void testUploadImageSuccess() throws Exception {
        // Arrange
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());
        String baseUrl = "https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/";

        when(mockRestTemplate.postForEntity(any(String.class), any(), any(Class.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        // Act
        String result = imageAPI.uploadImage(mockFile);

        // Assert
        assertTrue(result.startsWith(baseUrl), "URL should start with the base : https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/");
        String uuidPart = result.substring(baseUrl.length());
        assertTrue(isValidUUID(uuidPart), "The URL should contain a valid UUID");
    }

    @Test
    void testUploadImageFailure() {
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image content".getBytes());

        when(mockRestTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        String result = imageAPI.uploadImage(mockFile);

        assertEquals("URL://error-while-uploading-image", result);
    }

    @Test
    void testUploadImageWithNullFile() {
        String result = imageAPI.uploadImage(null);

        assertEquals("URL://error-while-uploading-image", result);
    }

    private boolean isValidUUID(String uuid) {
        String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        Pattern pattern = Pattern.compile(uuidRegex);
        return pattern.matcher(uuid).matches();
    }
}

