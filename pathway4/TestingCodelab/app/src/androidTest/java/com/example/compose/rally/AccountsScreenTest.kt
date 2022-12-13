package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.data.UserData
import com.example.compose.rally.ui.accounts.AccountsBody
import org.junit.Rule
import org.junit.Test

/**
 * Created by okwon on 2022/12/12.
 * Description :
 */
class AccountsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun accountScreen_alertsDisplayed() {
        composeTestRule.setContent {
            AccountsBody(UserData.accounts)
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("accountScreen_alertsDisplayed")

        composeTestRule
            .onNode(
                hasText("Vacation"), useUnmergedTree = true
            )
            .assertExists()
    }

}