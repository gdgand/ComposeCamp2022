package com.example.compose.rally

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.overview.OverviewScreen

@Composable
fun RallyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
// 테스트 및 재사용 가능한 코드를 만들려면 컴포저블에 navController 전체를 직접 전달하는 대신
// 트리거하려는 정확한 탐색 동작을 정의하는 콜백을 사용해야 합나디.
        navController = navController,
        startDestination = Overview.route,
        // modifier = Modifier.padding(innerPadding)
        modifier = modifier
    ) {
        // builder parameter will be defined here as the graph
        composable(route = Overview.route) {
            // Overview.screen()
            OverviewScreen(
                onClickSeeAllAccounts = {
                    navController.navigateSingleTopTo(Accounts.route)
                },
                onClickSeeAllBills = {
                    navController.navigateSingleTopTo(Bills.route)
                },
                onAccountClick = { accountType ->
                    navController
                        //.navigateSingleTopTo("${SingleAccount.route}/$accountType")
                        // 확장함수로 뺌
                        .navigateToSingleAccount(accountType)
                }
            )
        }
        composable(route = Accounts.route) {
            // Accounts.screen()
            AccountsScreen(
                onAccountClick = { accountType ->
                    navController
                        //.navigateSingleTopTo("${SingleAccount.route}/$accountType")
                        .navigateToSingleAccount(accountType)
                }
            )
        }
        composable(route = Bills.route) {
            // Bills.screen()
            BillsScreen()
        }
        composable(
            // 인수를 이용해 정보를 전달
            // "route/{argument}" pattern
            // route = "${SingleAccount.route}/{${SingleAccount.accountTypeArg}}",
            // SingleAccount 내부에 경로도 포함시킴
            route = SingleAccount.routeWithArgs,
//                    arguments = listOf(
//                        // 코드를 더 안전하게 만들고 예와 케이스를 처리하기 위한 타입 명시적으로 지정
//                        navArgument(SingleAccount.accountTypeArg) { type = NavType.StringType }
//                    )
            // SingleAccount 내부로 이동
            arguments = SingleAccount.arguments,
//                    deepLinks = listOf(navDeepLink {
//                        uriPattern = "rally://${SingleAccount.route}/{${SingleAccount.accountTypeArg}}"
//                    })
            deepLinks = SingleAccount.deepLinks
        ) { navBackStackEntry ->
            // retrieve the passed argument
            val accountType =
                navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)

            // Pass accountType to SingleAccountScreen
            SingleAccountScreen(accountType)
        }
    }
}

// navigation 확장 함수
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // popUpTo ~ saveState -> 탭을 선택했을 때 백스택에 대규모의 스택이 쌓이지 않도록
        // 그래프의 시작 대상을 팝업했을때 start desination 으로 가도록 만듬
        // 뒤로가기를 눌렀을때 모든 백스택이 팝되고 overview 화면으로 이동
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // 백스택 위에 대상의 사본이 최대 1개가 되도록
        launchSingleTop = true
        // 이 탐색 동작 이전에 PopUpToBuilder.saveState 또는 popUpToSaveState 속성에 의해 저장된
        // 상태를 복원하는지 여부를 정함. 이동할 대상의 id 를 사용하여 이전에 저장된 상태가 없다면 이 옵션은 효과가 없음
        restoreState = true
        // -> 결과: 동일한 탭을 다시 탭하면 이전 데이터와 사용자 상태가 다시 로드되지 않고 화면에 그대로 유지지
    }

fun NavHostController.navigateToSingleAccount(accountType: String) {
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}
