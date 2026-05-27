package deoca.hhv.transport.config;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@Getter
@Setter
public class MinioConfig {

    // Lấy thông tin URL từ file application.properties
    @Value("${minio.url}")
    private String url;
    @Value("${minio.access-key}")
    private String accessKey;
    @Value("${minio.secret-key}")
    private String secretKey;

    private final Logger log = LoggerFactory.getLogger(MinioConfig.class);

    @Bean
    public MinioClient minioClient(){
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(url)
                    .credentials(accessKey, secretKey)
                    .build();
            log.info("Kết nối thành công tới url:{}", url);
            return minioClient;
        } catch (Exception e){
            throw new RuntimeException("Có lỗi khi kết nối minIO{}", e);
        }
    }
}
