package de.unia.se.teamcq.vehiclestate.service

import de.unia.se.teamcq.TestUtils.getTestFleetReferenceModel
import de.unia.se.teamcq.security.service.IAuthenticationTokenAdapter
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils

@SpringBootTest
class VssAdapterTest : StringSpec() {

    @Autowired
    private lateinit var authenticationTokenAdapter: IAuthenticationTokenAdapter

    @Autowired
    private lateinit var vssAdapter: VssAdapter

    init {
        MockKAnnotations.init(this)

        "Fetch states for FleetReferences should work".config(enabled = true) {

            ReflectionTestUtils.setField(authenticationTokenAdapter, "authenticationUsername", "admin")
            ReflectionTestUtils.setField(authenticationTokenAdapter, "authenticationPassword", "fd123!")

            val fleetReferences = listOf(getTestFleetReferenceModel())
            val fetchedVehicles = vssAdapter.getNewVehicleStates(fleetReferences)

            fetchedVehicles.size shouldBe 5000
        }
    }
}
