package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.TestUtils.getTestFleetReferenceModel
import de.unia.se.teamcq.security.service.IAuthenticationTokenService
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils

@SpringBootTest
class VssAdapterTest : StringSpec() {

    @Autowired
    private lateinit var authenticationTokenService: IAuthenticationTokenService

    @Autowired
    private lateinit var vssAdapter: VssAdapter

    init {
        MockKAnnotations.init(this)

        "Fetch states for FleetReferences should work".config(enabled = true) {

            ReflectionTestUtils.setField(authenticationTokenService, "authenticationUsername", "admin")
            ReflectionTestUtils.setField(authenticationTokenService, "authenticationPassword", "fd123!")

            val fleetReferences = setOf(getTestFleetReferenceModel())
            val fetchedVehicles = vssAdapter.getNewVehicleStates(fleetReferences)

            fetchedVehicles.size shouldBe 5000
        }
    }
}
