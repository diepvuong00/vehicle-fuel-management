package deoca.hhv.transport.report.controller;

import deoca.hhv.transport.report.service.VehicleReportService;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import deoca.hhv.transport.vehicle.enums.VehicleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/reports")
@RestController
@RequiredArgsConstructor
public class ReportController {

    private final VehicleReportService vehicleReportService;


    /**
     * Xuất danh sách phương tiện Excel
     *
     * Ví dụ:
     * GET /api/reports/vehicles/excel
     *
     * GET /api/reports/vehicles/excel?status=ACTIVE
     *
     * GET /api/reports/vehicles/excel?status=MAINTENANCE
     */
    @GetMapping("/vehicles/excel")
    public ResponseEntity<InputStreamResource> exportVehicleExcel(
            @RequestParam(required = false)
            VehicleStatus status
    ){
        return vehicleReportService.exportVehicleExcel(status);
    }
}
