package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.security.service.IAuthenticationTokenService
import io.kotlintest.should
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils

@SpringBootTest
class VehicleStateServiceTest : StringSpec() {

    @Autowired
    private lateinit var authenticationTokenService: IAuthenticationTokenService

    @Autowired
    private lateinit var vehicleStateService: VehicleStateService

    init {
        MockKAnnotations.init(this)

        "ImportNewVehicleData" should {
            "Import new VehicleStates correctly when the API-Mock is running".config(enabled = false) {

                ReflectionTestUtils.setField(authenticationTokenService, "authenticationUsername", "admin")
                ReflectionTestUtils.setField(authenticationTokenService, "authenticationPassword", "fd123!")

                vehicleStateService.importNewVehicleData()
            }
        }
    }
}
