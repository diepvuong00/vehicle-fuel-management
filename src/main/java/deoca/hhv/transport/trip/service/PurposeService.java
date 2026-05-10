package deoca.hhv.transport.trip.service;

import deoca.hhv.transport.trip.dto.reponse.PurposeResponse;
import deoca.hhv.transport.trip.dto.request.PurposeRequest;

public interface PurposeService {

    PurposeResponse create(PurposeRequest request);
}
