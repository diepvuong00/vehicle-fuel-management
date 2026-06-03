package deoca.hhv.transport.report.service.impl;

import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.fuel.repository.FuelIssueRepository;
import deoca.hhv.transport.report.dto.vehicle.VehicleExportDto;
import deoca.hhv.transport.report.exporter.excel.VehicleExcelExporter;
import deoca.hhv.transport.report.service.VehicleReportService;
import deoca.hhv.transport.trip.entity.FuelNorm;
import deoca.hhv.transport.trip.repository.FuelNormRepository;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import deoca.hhv.transport.vehicle.enums.VehicleStatus;
import deoca.hhv.transport.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleReportServiceImpl
        implements VehicleReportService {

    private final VehicleRepository vehicleRepository;

    private final FuelNormRepository fuelNormRepository;

    private final FuelIssueRepository fuelIssueRepository;

    private final VehicleExcelExporter vehicleExcelExporter;


    @Override
    public ResponseEntity<InputStreamResource>
    exportVehicleExcel(
            VehicleStatus status) {

        List<Vehicle> vehicles;

        if(status == null){

            vehicles =
                    vehicleRepository
                            .findByDeletedFalse();

        }
        else{

            vehicles =
                    vehicleRepository
                            .findByStatusAndDeletedFalse(
                                    status
                            );
        }

        List<VehicleExportDto> rows =
                new ArrayList<>();

        for(Vehicle vehicle : vehicles){

            /*
             * loại nhiên liệu cấp phát gần nhất
             */
            FuelIssue latestIssue =
                    fuelIssueRepository
                            .findTopByVehicleIdOrderByFuelTimeDesc(
                                    vehicle.getId()
                            )
                            .orElse(null);

            /*
             * toàn bộ định mức
             */
            List<FuelNorm> norms =
                    fuelNormRepository
                            .findByVehicleIdAndActiveTrue(
                                    vehicle.getId()
                            );

            Double distanceNorm = null;

            Double workingHourNorm = null;

            Double idleHourNorm = null;

            for(FuelNorm norm : norms){

                switch (norm.getPurpose().getUnit()){

                    case KM_100:

                        distanceNorm =
                                norm.getNormValue();

                        break;

                    case HOUR_ACTIVITY:

                        workingHourNorm =
                                norm.getNormValue();

                        break;

                    case HOUR_MAINTAIN:

                        idleHourNorm =
                                norm.getNormValue();

                        break;
                }
            }

            VehicleExportDto dto =
                    VehicleExportDto.builder()

                            .licensePlate(
                                    vehicle.getLicensePlate()
                            )

                            .vehicleType(
                                    vehicle.getVehicleType()
                            )

                            .fuelType(
                                    latestIssue != null
                                            ? latestIssue.getFuelType().name()
                                            : ""
                            )

                            .distanceNorm(
                                    distanceNorm
                            )

                            .workingHourNorm(
                                    workingHourNorm
                            )

                            .idleHourNorm(
                                    idleHourNorm
                            )

                            .status(
                                    vehicle.getStatus()
                                            .getDescription()
                            )

                            .build();

            rows.add(dto);
        }

        return vehicleExcelExporter.export(
                rows
        );

    }
}
