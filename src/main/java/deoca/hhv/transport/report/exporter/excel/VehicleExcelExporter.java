package deoca.hhv.transport.report.exporter.excel;

import com.google.common.net.HttpHeaders;
import deoca.hhv.transport.report.dto.vehicle.VehicleExportDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VehicleExcelExporter {

    public ResponseEntity<InputStreamResource>
    export(
            List<VehicleExportDto> vehicles
    ){

//        Apache POI:


        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out =
                        new ByteArrayOutputStream()
        ) {

            Sheet sheet =
                    workbook.createSheet(
                            "Danh sách phương tiện"
                    );

            createHeader(workbook, sheet);

            int rowIndex = 1;

            int stt = 1;

            for (VehicleExportDto vehicle : vehicles) {

                Row row =
                        sheet.createRow(rowIndex++);

                writeDataRow(
                        row,
                        stt++,
                        vehicle
                );
            }

            autoSize(sheet);

            workbook.write(out);

            ByteArrayInputStream input =
                    new ByteArrayInputStream(
                            out.toByteArray()
                    );

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=vehicle-report.xlsx"
                    )
                    .contentType(
                            MediaType.parseMediaType(
                                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                            )
                    )
                    .body(
                            new InputStreamResource(
                                    input
                            )
                    );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Export Excel failed",
                    e
            );
        }
    }

    private void createHeader(
            Workbook workbook,
            Sheet sheet) {

        CellStyle style =
                workbook.createCellStyle();

        Font font =
                workbook.createFont();

        font.setBold(true);

        style.setFont(font);

        Row header =
                sheet.createRow(0);

        String[] columns = {

                "STT",
                "Biển số",
                "Loại xe",
                "Loại nhiên liệu",
                "L/100Km",
                "Lít/giờ hoạt động",
                "Lít/giờ nổ máy",
                "Trạng thái"
        };

        for (int i = 0; i < columns.length; i++) {

            Cell cell =
                    header.createCell(i);

            cell.setCellValue(
                    columns[i]
            );

            cell.setCellStyle(
                    style
            );
        }
    }

    private void writeDataRow(
            Row row,
            int stt,
            VehicleExportDto dto) {

        row.createCell(0)
                .setCellValue(stt);

        row.createCell(1)
                .setCellValue(dto.getLicensePlate());

        row.createCell(2)
                .setCellValue(dto.getVehicleType());

        row.createCell(3)
                .setCellValue(dto.getFuelType());

        if(dto.getDistanceNorm() != null){
            row.createCell(4)
                    .setCellValue(dto.getDistanceNorm());
        }

        if(dto.getWorkingHourNorm() != null){
            row.createCell(5)
                    .setCellValue(dto.getWorkingHourNorm());
        }

        if(dto.getIdleHourNorm() != null){
            row.createCell(6)
                    .setCellValue(dto.getIdleHourNorm());
        }

        row.createCell(7)
                .setCellValue(dto.getStatus());
    }

    private void autoSize(
            Sheet sheet) {

        for(int i = 0 ; i < 8 ; i++) {

            sheet.autoSizeColumn(i);
        }
    }
}
