package deoca.hhv.transport.driver.service;

import deoca.hhv.transport.driver.dto.reponse.DriverResponse;
import deoca.hhv.transport.driver.dto.request.DriverRequest;

public interface DriverService {

    DriverResponse createDriver(DriverRequest request);

}
