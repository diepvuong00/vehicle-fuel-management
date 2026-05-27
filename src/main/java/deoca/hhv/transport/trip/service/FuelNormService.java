package deoca.hhv.transport.trip.service;

import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.trip.dto.reponse.FuelNormResponse;
import deoca.hhv.transport.trip.dto.request.FuelNormRequest;

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
}
