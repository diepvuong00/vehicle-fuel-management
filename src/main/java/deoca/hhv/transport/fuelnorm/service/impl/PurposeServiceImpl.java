package deoca.hhv.transport.fuelnorm.service.impl;

import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import deoca.hhv.transport.fuelnorm.dto.reponse.PurposeResponse;
import deoca.hhv.transport.fuelnorm.dto.request.PurposeRequest;
import deoca.hhv.transport.fuelnorm.entity.Purpose;
import deoca.hhv.transport.fuelnorm.repository.PurposeRepository;
import deoca.hhv.transport.fuelnorm.service.PurposeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PurposeServiceImpl implements PurposeService {

    private final PurposeRepository repository;


    @Override
    public PurposeResponse create(
            PurposeRequest request
    ) {

        // validate tên
        if (
                repository.existsByName(
                        request.getName().trim()
                )
        ) {

            throw new AppException(
                    ErrorCode.PURPOSE_NAME_ALREADY_EXISTS
            );
        }

        String generatedCode =
                generatePurposeCode();

        Purpose purpose =
                Purpose.builder()
                        .code(generatedCode)
                        .name(
                                request.getName().trim()
                        )
                        .unit(
                                request.getUnit()
                        )
                        .applyFuelNorm(
                                request.getApplyFuelNorm() != null
                                        ? request.getApplyFuelNorm()
                                        : true
                        )
                        .description(
                                request.getDescription()
                        )
                        .active(true)
                        .build();

        repository.save(
                purpose
        );

        return mapToResponse(
                purpose
        );
    }

    @Override
    public List<PurposeResponse> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PurposeResponse mapToResponse(Purpose purpose) {
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

    private String generatePurposeCode() {

        String lastCode =
                repository.findTopByOrderByCodeDesc()
                        .map(Purpose::getCode)
                        .orElse(null);

        if (lastCode == null) {
            return "PUR001";
        }

        int number =
                Integer.parseInt(
                        lastCode.substring(3)
                );

        return String.format(
                "PUR%03d",
                number + 1
        );
    }
}
