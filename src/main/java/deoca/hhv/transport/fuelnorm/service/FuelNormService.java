package deoca.hhv.transport.fuelnorm.service;

import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.fuelnorm.dto.reponse.FuelNormResponse;
import deoca.hhv.transport.fuelnorm.dto.request.FuelNormRequest;

public interface FuelNormService {

    FuelNormResponse create(FuelNormRequest request);

    PageResponse<FuelNormResponse> getAll(
            int page,
            int size,
            String vehicleId,
            String purposeId
    );

    FuelNormResponse getById(String id);

    FuelNormResponse update(String id, FuelNormRequest request);

    void delete(String id);

//    List<FuelNorm> findByVehicleIdAndActiveTrue(
//            UUID vehicleId);
}
