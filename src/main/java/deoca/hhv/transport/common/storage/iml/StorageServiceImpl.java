package deoca.hhv.transport.common.storage.iml;

import deoca.hhv.transport.common.storage.service.StorageService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private  String bucket;

    @Override
    public String uploadVehicleImage(MultipartFile file) {

        try {
            String fileName=
                    UUID.randomUUID()
                            +"_"
                            +file.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs
                            .builder()

                            .bucket(bucket)

                            .object(fileName)

                            .stream(
                                    file.getInputStream(),
                                    file.getSize(),
                                    -1
                            )

                            .contentType(
                                    file.getContentType()
                            )

                            .build()
            );

            return fileName;
        } catch (Exception e){
            throw new RuntimeException("Tải ảnh lên thất bại");
        }
    }

    @SneakyThrows
    @Override
    public String getImageUrl(String key) {

        return minioClient.getPresignedObjectUrl(

                GetPresignedObjectUrlArgs

                        .builder()

                        .method(Method.GET)

                        .bucket(bucket)

                        .object(key)

                        .expiry(1,
                                TimeUnit.DAYS)
                        .build()

        );
    }
}
