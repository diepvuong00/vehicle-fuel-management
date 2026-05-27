package deoca.hhv.transport.common.storage.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String uploadVehicleImage(MultipartFile file);
    String getImageUrl(String key);
}
