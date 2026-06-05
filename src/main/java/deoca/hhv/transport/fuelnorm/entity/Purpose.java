package deoca.hhv.transport.fuelnorm.entity;

import deoca.hhv.transport.fuelnorm.enums.PurposeUnit;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "purposes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_purpose_code",
                        columnNames = "code"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purpose {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /*
     * Mã mục đích
     */
    @Column(nullable = false, unique = true)
    private String code;

    /*
     * Tên mục đích
     */
    @Column(nullable = false)
    private String name;

    /*
     * Đơn vị
     * L/100KM
     * L/HOUR hoạt động
     * L/HOUR nổ máy
     */
    @Enumerated(EnumType.STRING)
    private PurposeUnit unit;

    /*
     * Có áp dụng định mức không
     */
    @Column(name = "apply_fuel_norm")
    private Boolean applyFuelNorm;

    @OneToMany(mappedBy = "purpose")
    private List<FuelNorm> fuelNorms = new ArrayList<>();

    /*
     * Mô tả
     */
    private String description;

    /*
     * Trạng thái
     */
    private Boolean active;
}
