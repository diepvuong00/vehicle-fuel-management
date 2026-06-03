package deoca.hhv.transport.fuel.service;

import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.fuel.dto.reponse.FuelIssueResponse;
import deoca.hhv.transport.fuel.dto.request.FuelIssueRequest;

public interface FuelIssueService {

    FuelIssueResponse create(FuelIssueRequest request);

    PageResponse<FuelIssueResponse> getAll(
            int page,
            int size,
            String status,
            String vehicleId,
            String driverId,
            String keyword
    );

    FuelIssueResponse update(String id, FuelIssueRequest request);

    void delete(String id);

    FuelIssueResponse getById(String id);


}
