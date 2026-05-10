package deoca.hhv.transport.trip.service.impl;

import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import deoca.hhv.transport.trip.dto.reponse.PurposeResponse;
import deoca.hhv.transport.trip.dto.request.PurposeRequest;
import deoca.hhv.transport.trip.entity.Purpose;
import deoca.hhv.transport.trip.repository.PurposeRepository;
import deoca.hhv.transport.trip.service.PurposeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PurposeServiceImpl implements PurposeService {

    private final PurposeRepository repository;


    @Override
    public PurposeResponse create(PurposeRequest request) {

        /*
         * Validate code duplicate
         */
        if (repository.existsByCode(request.getCode())) {

            throw new AppException(ErrorCode.PURPOSE_CODE_ALREADY_EXISTS);
        }

        /*
         * Validate name duplicate
         */
        if (repository.existsByName(request.getName())) {

            throw new AppException(ErrorCode.PURPOSE_NAME_ALREADY_EXISTS);
        }

        /*
         * Create purpose
         */
        Purpose purpose = Purpose.builder()
                .code(request.getCode().trim().toUpperCase())
                .name(request.getName().trim())
                .unit(request.getUnit())
                .applyFuelNorm(
                        request.getApplyFuelNorm() != null
                                ? request.getApplyFuelNorm()
                                : true
                )
                .description(request.getDescription())
                .active(true)
                .build();

        repository.save(purpose);

        /*
         * Response
         */
        return PurposeResponse.builder()
                .id(purpose.getId())
                .code(purpose.getCode())
                .name(purpose.getName())
                .unit(purpose.getUnit().name())
                .applyFuelNorm(purpose.getApplyFuelNorm())
                .description(purpose.getDescription())
                .active(purpose.getActive())
                .build();
    }

}
