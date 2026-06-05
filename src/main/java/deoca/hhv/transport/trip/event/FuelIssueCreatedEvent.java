package deoca.hhv.transport.trip.event;

import deoca.hhv.transport.fuel.entity.FuelIssue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FuelIssueCreatedEvent {

    private FuelIssue fuelIssue;
}
