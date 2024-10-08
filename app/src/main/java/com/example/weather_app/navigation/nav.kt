package com.example.weather_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.weather_app.admin.AdminHome
import com.example.weather_app.admin.AdminLogin
import com.example.weather_app.admin.knowledge.AddKnowledgeScreen
import com.example.weather_app.admin.knowledge.KnowledgeList
import com.example.weather_app.admin.news.AddNewsScreen
import com.example.weather_app.admin.news.NewsList
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.screens.Data
import com.example.weather_app.ui.theme.screens.Diseases
import com.example.weather_app.ui.theme.screens.Home
import com.example.weather_app.ui.theme.screens.Login
import com.example.weather_app.ui.theme.screens.news.News
import com.example.weather_app.ui.theme.screens.OTP
import com.example.weather_app.ui.theme.screens.Signup
import com.example.weather_app.ui.theme.screens.WeatherS
import com.example.weather_app.ui.theme.screens.knowledge.Knowledge
import com.example.weather_app.ui.theme.screens.knowledge.KnowledgeDetailScreen
import com.example.weather_app.ui.theme.screens.news.NewsDetailScreen
import com.example.weather_app.viewmodel.KnowledgeViewModel
import com.example.weather_app.viewmodel.LoginViewModel
import com.example.weather_app.viewmodel.NewsViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun navGraph(navController: NavHostController) {
    val loginViewModel: LoginViewModel = viewModel()
    val newsViewModel: NewsViewModel = viewModel()
    val knodledgeViewModel: KnowledgeViewModel = viewModel()
    val newsList by newsViewModel.allNews.observeAsState(emptyList())

    NavHost(navController = navController, startDestination = Screens.Login.screens) {
        composable(Screens.ManageNews.screens) {
            NewsList(navController)
        }
        composable(Screens.ManageKnowledge.screens){
            KnowledgeList(navController)
        }
        composable(Screens.AdminLogin.screens) {
            AdminLogin(navController = navController)
        }
        composable(Screens.Data.screens) {
            Data()
        }
        composable(Screens.Weather.screens) {
            WeatherS(navController)
        }
        composable(Screens.Diseases.screens) {
            Diseases()
        }
        composable(Screens.News.screens) {
            News(navController, newsViewModel)
        }
        composable(Screens.Knowledge.screens){
            Knowledge(navController, knodledgeViewModel)
        }
        composable(Screens.Login.screens) {
            Login(navController, loginViewModel)
        }
        composable(Screens.SignUp.screens) {
            Signup(navController)
        }
        composable(Screens.AddNews.screens) {
            AddNewsScreen(navController, viewModel = newsViewModel)
        }
        composable(Screens.AddKnowledge.screens){
            AddKnowledgeScreen(navController, knodledgeViewModel)
        }
        composable(
            Screens.AdminHome.screens,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            AdminHome(navController, email!!)
        }
        composable(
            Screens.Otp.screens,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            val password = navBackStackEntry.arguments?.getString("password")
            OTP(navController = navController, email = email!!, password = password!!)
        }
        composable(
            Screens.Home.screens,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            Home(navController = navController, email = email!!)
        }
        composable(
            Screens.NewsDetail.screens,
            arguments = listOf(navArgument("title"){type = NavType.StringType})
        ){
            navBackStackEntry ->
            val title = navBackStackEntry.arguments?.getString("title")
            NewsDetailScreen(navController= navController, viewModel = newsViewModel, title = title!!)
        }
        composable(
            Screens.KnowledgeDetail.screens,
            arguments = listOf(navArgument("title"){type = NavType.StringType})
        ){
                navBackStackEntry ->
            val title = navBackStackEntry.arguments?.getString("title")
            KnowledgeDetailScreen(navController= navController, viewModel = knodledgeViewModel, title = title!!)
        }
    }
}
