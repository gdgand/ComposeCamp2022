package java.com.example.compose.rally

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.compose.rally.RellyNavHost
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Before
    fun setupRallyNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)

            navController.navigatorProvider.addNavigator(ComposeNavigator())
            RellyNavHost(navController = navController)
        }
    }

    @Test
    fun rallyNavHost_verifyOverviewStartDestination() {
        composeTestRule.onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }
    @Test
    fun rallyNavHost_clickAllAccount_navigatesToAccounts() {
        composeTestRule.onNodeWithContentDescription("All Accounts")
            .performClick()

        composeTestRule.onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_clickAllBills_navigateToBills() {
        composeTestRule.onNodeWithContentDescription("All Bills")
            .performScrollTo()
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(route, "bills")
    }
}