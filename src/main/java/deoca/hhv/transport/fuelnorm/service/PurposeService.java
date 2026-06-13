package deoca.hhv.transport.fuelnorm.service;

import deoca.hhv.transport.fuelnorm.dto.reponse.PurposeResponse;
import deoca.hhv.transport.fuelnorm.dto.request.PurposeRequest;

import java.util.List;

public interface PurposeService {

    PurposeResponse create(PurposeRequest request);
    List<PurposeResponse> getAll();

    void deletePurpose(String id);
}
