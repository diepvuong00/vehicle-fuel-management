package deoca.hhv.transport.trip.service;

import deoca.hhv.transport.trip.dto.reponse.FuelNormResponse;
import deoca.hhv.transport.trip.dto.request.FuelNormRequest;

public interface FuelNormService {

    FuelNormResponse create(FuelNormRequest request);
}
