package com.example.weather_app.ui.theme.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.ModifierLocal
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather_app.R
import com.example.weather_app.model.Screens
import com.example.weather_app.ui.theme.BlueJC
import com.example.weather_app.ui.theme.GreenJC
import com.example.weather_app.ui.theme.WeatherCard
import com.example.weather_app.viewmodel.WeatherViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun Home(navController: NavHostController, email: String){
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var startX by remember { mutableStateOf(0f) }  // Initial X for dragging
    var startY by remember { mutableStateOf(0f) }  // Initial Y for dragging
    var isDragging by remember { mutableStateOf(false) }
    var bg = R.drawable.background;
//    val images = listOf(
//        R.drawable.firefly,
//        R.drawable.marisa
//    )
    val images = listOf(
        R.drawable.machine
    )
    val pagerState = rememberPagerState(pageCount = { images.size })
    LaunchedEffect(Unit){
        while (true){
            delay(2000)
            val nextPage = (pagerState.currentPage + 1)%pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    weatherData?.let {
        if(it.main.temp.toInt() > 32){
            bg = R.drawable.sunny_bg
        }
        else if(it.main.temp.toInt()<31&&it.main.temp.toInt() > 20)
            bg = R.drawable.haze_bg
        else bg = R.drawable.background
    }
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = currentDate.format(formatter)
    val dayOfWeek = currentDate.dayOfWeek
    var city by remember {
        mutableStateOf("")
    }
    val apiKey = "bd5ffc0a924060bd54f9267fa6f6ec4f"
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier
                    .background(BlueJC)
                    .fillMaxWidth()
                    .height(150.dp)){
                    Text(text = "Hello")
                }
                Divider()
                NavigationDrawerItem(label = { Text(text = "Home", color = BlueJC) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navController.navigate(Screens.Home.screens) {
                            popUpTo(0)
                        }},
                    icon = { Icon(imageVector = Icons.Default.Home,
                        contentDescription = "home",
                        tint = BlueJC)
                    })
                NavigationDrawerItem(label = { Text(text = "Logout", color = BlueJC) },
                    selected = false,
                    onClick = {
                        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.clear()
                        editor.apply()
                        navController.navigate(Screens.Login.screens)
                    },
                    icon = { Icon(imageVector = Icons.Default.Home,
                        contentDescription = "logout",
                        tint = BlueJC)
                    })
            }
        }
    ) {
        Scaffold(
            topBar = {

                TopAppBar(title = { Text(text = "Hello") },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = BlueJC,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = "MenuButton"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            Toast.makeText(
                                context,
                                "Notification",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.NotificationsActive,
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = {
                            Toast.makeText(
                                context,
                                "Notification",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.PersonOutline,
                                contentDescription = "Localized description",
                            )
                        }
                    })

            },
            bottomBar = {

                BottomAppBar(
                    containerColor = BlueJC
                ) {
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Home
                            navController.navigate(Screens.Home.screens)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Home) Color.White else Color.DarkGray
                        )
                    }
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.List
                            navController.navigate(Screens.Data.screens)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.List) Color.White else Color.DarkGray
                        )
                    }
                }
            }
        ) {
                paddingValues ->  Box(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = bg),
                contentScale = ContentScale.FillBounds
            )
            .padding(paddingValues)){
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),
//            .clip(RoundedCornerShape(20.dp))
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {
                viewModel.fetchWeather("Da Nang", apiKey)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(BlueJC),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    weatherData?.let {
                        Text(text = "  ${it.main.temp.toInt()}°C ☁︎⋅",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)
                    }

                    Column(
                        modifier = Modifier.height(37.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(text = "DA NANG")
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(text = "${dayOfWeek}, ${formattedDate}")
                    }
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)) {
                    HorizontalPager(state = pagerState,
                        modifier = Modifier.wrapContentSize()) {
                            currentPage -> Card(
                        modifier = Modifier.wrapContentSize(),
//                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Image(painter = painterResource(id = images[currentPage]),
                            contentDescription = "", modifier = Modifier.fillMaxSize())
                    }
                    }

                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    WeatherCard(label = "Supervise", icon = Icons.Default.WarningAmber, onClick = {
                        navController.navigate(Screens.Diseases.screens)
                    })
                    WeatherCard(label = "Weather", icon = Icons.Default.Cloud, onClick = {
                        navController.navigate(Screens.Weather.screens)
                    })
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    WeatherCard(label = "Knowledge", icon = Icons.Default.Book, onClick = {
                        navController.navigate(Screens.Knowledge.screens)
                    })
                    WeatherCard(label = "News", icon = Icons.Default.Newspaper, onClick = {
                        navController.navigate(Screens.News.screens)
                    })
                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()  // Make the Box take up the full screen
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = {
                                    // Start dragging from current position
                                    startX = offsetX
                                    startY = offsetY
                                    isDragging = true
                                },
                                onDragEnd = {
                                    // End of drag interaction
                                    isDragging = false
                                },
                                onDragCancel = {
                                    // Drag got canceled (optional handling)
                                    isDragging = false
                                },
                                onDrag = { change, dragAmount ->
                                    // Handle the drag event, update offsets based on movement
                                    change.consume() // Consume the drag gesture event to prevent further propagation
                                    offsetX += dragAmount.x // Update X offset based on drag
                                    offsetY += dragAmount.y // Update Y offset based on drag
                                }
                            )
                        }
                ) {
                    // Floating Action Button inside the Box
                    FloatingActionButton(
                        onClick = { /* Handle click event */ },
                        modifier = Modifier
                            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }  // Offset FAB based on dragging
                            .size(56.dp),
                        shape = CircleShape,
                        containerColor = BlueJC
                    ) {
                        Icon(Icons.Filled.Message, contentDescription = "Add")
                    }
                }
            }
        }

        }
    }
}

//@Composable
//fun PageIndicator(pageCount: Int, currentPage: Int) {
//    Row(
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//        repeat(pageCount) {
//            IndicatorDots(isSelected = it == currentPage)
//        }
//    }
//}
//
//@Composable
//fun IndicatorDots(isSelected: Boolean) {
//    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp
//        , label ="")
//    Box(modifier = Modifier
//        .padding(2.dp)
//        .size(size.value)
//        .clip(CircleShape)
//        .background(if (isSelected) Color(0xff373737) else Color(0xA8373737)))
//}

@Preview(showSystemUi = true)
@Composable
fun MovableFAB() {

}
