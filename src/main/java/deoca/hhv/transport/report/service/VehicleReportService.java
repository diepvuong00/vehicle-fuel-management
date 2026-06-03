package deoca.hhv.transport.report.service;

import deoca.hhv.transport.vehicle.enums.VehicleStatus;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;

public interface VehicleReportService {

    ResponseEntity<InputStreamResource>
    exportVehicleExcel(
            VehicleStatus status);

}
