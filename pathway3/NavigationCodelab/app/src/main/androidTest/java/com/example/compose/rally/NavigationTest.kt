import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupRallyNavHost() {
        composeTestRule.setContent {
            navController =
                TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            RallyNavHost(navController = navController)
        }
    }

    @Test
    fun rallyNavHost_verifyOverviewStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_clickAllAccount_navigatesToAccounts() {
        composeTestRule
            .onNodeWithContentDescription("All Accounts")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_clickAllBills_navigateToBills() {
        composeTestRule.onNodeWithContentDescription("All Bills")
            .performScrollTo()
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "bills")
    }
}