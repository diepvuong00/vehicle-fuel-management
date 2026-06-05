package deoca.hhv.transport.trip.listener;

import deoca.hhv.transport.trip.event.FuelIssueCreatedEvent;
import deoca.hhv.transport.trip.service.TripLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FuelIssueListener {

    private final TripLogService tripLogService;

    @EventListener
    public void handleFuelIssueCreated(
            FuelIssueCreatedEvent event
    ){

        tripLogService
                .addFuelIssueToTrip(
                        event.getFuelIssue()
                );
    }
}
