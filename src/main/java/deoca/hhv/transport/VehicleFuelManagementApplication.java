package deoca.hhv.transport;

import deoca.hhv.transport.driver.entity.Driver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VehicleFuelManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleFuelManagementApplication.class, args);
//		Driver driver = new Driver();
//		System.out.println(driver.getId()); // phải = null
	}

}
