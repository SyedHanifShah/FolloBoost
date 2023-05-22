package com.hanisoft.bestar.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hanisoft.bestar.R
import com.hanisoft.bestar.data.BeStarDataStore
import com.hanisoft.bestar.domain.item.BoostPackage
import com.hanisoft.bestar.util.CircularIndeterminateProgressBar
import com.hanisoft.bestar.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val titleFont = ResourcesCompat.getFont(context, R.font.pacifico_regular)
    val mainBold = ResourcesCompat.getFont(context, R.font.arbutus_slab)
    val robotoMedium = ResourcesCompat.getFont(context, R.font.roboto_medium)
    val robotoRegular = ResourcesCompat.getFont(context, R.font.roboto_regular)
    val compagion = viewModel.currentCompagion
    val history = viewModel.history
    val dataStore = BeStarDataStore(context)
    val openDialog = dataStore.getOpenDialogValue.collectAsState(initial = false)

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }
                is UiEvent.Navigate -> {

                }
                else -> Unit
            }
        }

    }

    Scaffold(scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        backgroundColor = Color(0xFF2C2C2C),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TikeTok", fontFamily = FontFamily(titleFont!!),
                        color = Color.White
                    )
                },
                actions = {
                    Row(modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .background(
                            color = Color(0xFF2C2C2C), RoundedCornerShape(30.dp)
                        )) {
                        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.coin), contentDescription = "coins",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(7.dp))

                            Text(
                                text = viewModel.getUserData?.coins ?: "0",
                                modifier = Modifier,
                                color = Color.White,
                                fontFamily = FontFamily(mainBold!!),
                                fontSize = 15.sp
                            )
                        }

                    }
                },
                backgroundColor = Color(0XFFF94C84)
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier

                .padding(vertical = 20.dp)
        ) {

            if (compagion != null) {
                Box() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {


                        Box(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 20.dp)
                                .diagonalGradientBorder(
                                    colors = listOf(
                                        Color(0xFFd71069),
                                        Color(0XFFF94C84),
                                        Color(0xFF7D21F3),
                                    ),
                                    shape = RoundedCornerShape(40.dp),
                                    isFromRight = true
                                )
                        ) {
                            Box(
                                contentAlignment = Alignment.Center, modifier = Modifier
                                    .size(92.dp)
                                    .padding(6.dp)
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(40.dp)
                                    )
                                    .clip(RoundedCornerShape(30.dp))
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(compagion.avatar)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(40.dp))
                                        .size(92.dp)
                                )
                            }

                        }
                        Text(
                            text = "@${compagion.uniqueId}",
                            color = Color.White,
                            fontFamily = FontFamily(mainBold!!),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 20.dp),
                            textAlign = TextAlign.Center
                        )


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {

                                Text(
                                    text = compagion.currentFollowers ?: "0",
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily(robotoMedium!!)
                                )

                                Text(
                                    text = "followers",
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(robotoRegular!!),
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(70.dp))

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = compagion.currentFollowing ?: "0",
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(robotoMedium!!),
                                    color = Color.White
                                )

                                Text(
                                    text = "following",
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(robotoRegular!!),
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }


                        }


                    }
                    if (viewModel.goToTitTokForFollow) {
                        CircularIndeterminateProgressBar(circleColor = Color(0XFFF94C84))
                    }

                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularIndeterminateProgressBar(circleColor = Color(0XFFF94C84))
                }
            }


            if (openDialog.value == true) {
                AlertDialog(
                    onDismissRequest = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveOpenDialogalue(false)
                        }
                    },
                    backgroundColor = Color(0XFFF94C84),
                    shape = RoundedCornerShape(20.dp),
                    title = {
                        Text(text = "Follow Not Detected", color = Color.White)
                    },
                    text = {
                        Column() {
                            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                Icon(painter = painterResource(id = R.drawable.check_mark), contentDescription = "check mark",
                                tint = Color(0xFF4CAF50),
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                Text("Login account must be same with TikTok", color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                Icon(painter = painterResource(id = R.drawable.check_mark), contentDescription = "check mark",
                                tint = Color(0xFF4CAF50),
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                Text("Follow the user", color = Color.White)
                            }
                        }
                    },
                    buttons = {
                        Row(
                            modifier = Modifier.padding(all = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        dataStore.saveOpenDialogalue(false)
                                    }
                                }
                            ) {
                                Text("Dismiss")
                            }
                        }
                    }
                )
            }



            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
            ) {

                Button(
                    onClick = {
                        viewModel.onEvent(HomeEvent.OnFollowClick)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(Color(0XFFF94C84)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Get Follow And Earn", color = Color.White)
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = { viewModel.onEvent(HomeEvent.OnSkipClick) },
                    modifier = Modifier
                        .fillMaxWidth(0.45f)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF7D21F3))
                ) {
                    Text(text = "Skip", color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(painter = painterResource(id = R.drawable.skip), contentDescription ="Skip",
                   modifier= Modifier.size(18.dp).padding(top = 2.dp), tint = Color.White
                        )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .shadow(elevation = 5.dp, RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors =
                            listOf(
                                Color(0xFF4E4D4D),
                                Color(0xFF222121)
                            ), start = Offset(x = 9f, y = 900f)
                        ), RoundedCornerShape(20.dp)
                    )
                    .padding(vertical = 20.dp, horizontal = 10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    val name = history?.packageName ?: "Nill"

                    Text(
                        text = name, color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(mainBold!!)
                    )
                    Text(
                        text = "Activated\nPackage", color = Color.Gray,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(robotoRegular!!)
                    )
                }
                Divider(
                    Modifier
                        .width(1.dp)
                        .height(30.dp),
                    color = Color(0XFFF94C84)
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    val remainFollower = history?.remainFollower ?: "0"
                    Text(
                        text = remainFollower, color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(mainBold!!)
                    )
                    Text(
                        text = "Remaining\nFollower", color = Color.Gray,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(robotoRegular!!)
                    )
                }
                Divider(
                    Modifier
                        .width(1.dp)
                        .height(30.dp),
                    color = Color(0XFFF94C84)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    val gainFollower = history?.gainFollwer ?: "0"
                    Text(
                        text = gainFollower, color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(mainBold!!)
                    )
                    Text(
                        text = "Gain\nFollowers", color = Color.Gray,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(robotoRegular!!)
                    )
                }


            }


            if (viewModel.boostPackagesList.boostPackages.isNotEmpty()) {
                LazyRow(
                    content = {
                        items(items = viewModel.boostPackagesList.boostPackages) {

                            BoostPackagesItem(it, viewModel, onPackageClick = { name, coins ->
                                viewModel.onEvent(HomeEvent.OnBoostPackageClick(name, coins))
                            })
                        }
                    },
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                )

            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                ) {

                    CircularIndeterminateProgressBar(circleColor = Color(0XFFF94C84))

                }
            }

        }
    }
}

@Composable
fun BoostPackagesItem(
    boostPackage: BoostPackage,
    viewModel: HomeViewModel,
    onPackageClick: (String, Int) -> Unit
) {
    val context = LocalContext.current
    val mainBold = ResourcesCompat.getFont(context, R.font.arbutus_slab)
    val robotoMedium = ResourcesCompat.getFont(context, R.font.roboto_medium)
    val robotoRegular = ResourcesCompat.getFont(context, R.font.roboto_regular)

    Box(contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0XFFF94C84),
                                Color(0xFF7D21F3)
                            ),
                            start = Offset(y = 50f, x = 500f)
                        ), RoundedCornerShape(20.dp)
                    )
                    .height(180.dp)
                    .padding(horizontal = 30.dp)
            ) {

                Text(
                    text = boostPackage.name,
                    fontFamily = FontFamily(mainBold!!),
                    fontSize = 16.sp,
                    color = Color.White
                )
                Row(horizontalArrangement = Arrangement.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.coin), contentDescription = "coins",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = boostPackage.coins,
                        color = Color.White,
                        fontFamily = FontFamily(mainBold),
                        fontSize = 15.sp
                    )
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = "Followers",
                        color = Color.White,
                        fontFamily = FontFamily(robotoRegular!!),
                        fontSize = 14.sp
                    )
                    Text(
                        text = ":",
                        color = Color.White,
                        fontFamily = FontFamily(robotoRegular!!),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = boostPackage.follower,
                        color = Color.White,
                        fontFamily = FontFamily(mainBold!!),
                        fontSize = 15.sp
                    )
                }

                Button(onClick = { onPackageClick(boostPackage.name, boostPackage.coins.toInt()) }) {
                    Text(text = "Activate")
                }
            }

        }
        if (viewModel.activatingBoost) {
            CircularIndeterminateProgressBar()
        }
    }


}


fun Modifier.diagonalGradientBorder(
    colors: List<Color>,
    borderSize: Dp = 2.dp,
    shape: Shape,
    isFromRight: Boolean = false
) = composed {

    val (start, end) = if (isFromRight) {
        Pair(
            Offset(100f, 0.0f),
            Offset(0.0f, 100.0f)
        )
    } else {
        Pair(Offset.Zero, Offset.Infinite)
    }

    border(
        width = borderSize,
        brush = Brush.linearGradient(colors = colors, start = start, end = end),
        shape = shape
    )
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}