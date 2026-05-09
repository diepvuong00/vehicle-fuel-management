package deoca.hhv.transport.fuel.service;

import deoca.hhv.transport.fuel.dto.reponse.FuelIssueResponse;
import deoca.hhv.transport.fuel.dto.request.FuelIssueRequest;

public interface FuelIssueService {

    FuelIssueResponse create(FuelIssueRequest request);
}
