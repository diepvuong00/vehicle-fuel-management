package deoca.hhv.transport.trip.calculator;

import deoca.hhv.transport.fuelnorm.entity.FuelNorm;
import deoca.hhv.transport.fuelnorm.enums.PurposeUnit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FuelNormCalculator {

    public Double calculate(
            Double totalKm,
            Double totalWorkingHour,
            Double totalIdleHour,
            List<FuelNorm> fuelNorms
    ) {

        double totalStandardFuel = 0D;

        for(FuelNorm norm : fuelNorms){

            PurposeUnit type =
                    norm.getPurpose()
                            .getUnit();

            switch (type){

                case KM_100 ->

                        totalStandardFuel +=
                                totalKm
                                        *
                                        norm.getNormValue()
                                        / 100;

                case HOUR_ACTIVITY ->

                        totalStandardFuel +=
                                totalWorkingHour
                                        *
                                        norm.getNormValue();

                case HOUR_MAINTAIN ->

                        totalStandardFuel +=
                                totalIdleHour
                                        *
                                        norm.getNormValue();
            }
        }

        return totalStandardFuel;
    }


}
