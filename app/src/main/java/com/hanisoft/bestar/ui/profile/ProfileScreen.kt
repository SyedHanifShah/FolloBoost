package com.hanisoft.bestar.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hanisoft.bestar.R
import com.hanisoft.bestar.ui.home.diagonalGradientBorder
import com.hanisoft.bestar.util.*

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,

    ) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val mainBold = ResourcesCompat.getFont(context, R.font.lilitaone_regular)
    val robotoMedium = ResourcesCompat.getFont(context, R.font.roboto_medium)
    val robotoRegular = ResourcesCompat.getFont(context, R.font.cairo_extralight)



    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }
                is UiEvent.PopBackStack -> onPopBackStack()

                else -> Unit
            }
        }
    }



    Scaffold(scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "back",
                        modifier = Modifier.clickable {viewModel.onEvent(ProfileEvent.OnbackClick) }
                    )
                },
                actions = {},
                backgroundColor = Color(0XFFF94C84)
            )
        }
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .background(Color(0xFF2C2C2C))
                .fillMaxSize()
                .padding(top = 20.dp, start = 40.dp, bottom = 150.dp, end = 40.dp)
        ) {
            if (viewModel.userInfo != null) {
                Column() {
                    if (viewModel.userInfo?.avatar?.url != "") {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 20.dp)
                                    .diagonalGradientBorder(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Transparent,
                                            Color.Transparent,
                                            Color(0xFFd71069),
                                            Color(0XFFF94C84),

                                            ),
                                        shape = RoundedCornerShape(50.dp),
                                        isFromRight = true
                                    )
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center, modifier = Modifier
                                        .size(120.dp)
                                        .padding(6.dp)
                                        .background(
                                            color = Color.LightGray,
                                            shape = RoundedCornerShape(50.dp)
                                        )
                                        .clip(RoundedCornerShape(30.dp))
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(viewModel.userInfo?.avatar?.url)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(50.dp))
                                            .size(120.dp)
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(top = 40.dp)
                            ) {
                                Divider(
                                    Modifier
                                        .width(1.dp)
                                        .height(70.dp),
                                    color = Color(0XFFF94C84)
                                )

                                Spacer(modifier = Modifier.width(40.dp))
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {

                                    Text(
                                        text = "Joined",
                                        fontFamily = FontFamily(robotoMedium!!),
                                        fontSize = 15.sp,
                                        color = Color.Gray
                                    )
                                    val time =
                                        viewModel.getUserData?.date?.toLong()
                                            ?.let { it1 -> setJoiningTime(it1) }
                                    if (time != null) {
                                        Text(
                                            text = time ,
                                            fontFamily = FontFamily(robotoRegular!!),
                                            fontSize = 15.sp,
                                            color = Color.White,
                                            modifier = Modifier
                                        )
                                    }


                                }


                            }


                        }

                    }



                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(vertical = 20.dp)
                    ) {
                        if (viewModel.userInfo?.name != "") {
                            viewModel.userInfo?.name?.let { it1 ->
                                Text(
                                    text = it1.toUpperCase(),
                                    fontFamily = FontFamily(mainBold!!),
                                    fontSize = 25.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Column(horizontalAlignment = Alignment.End, modifier = Modifier) {
                            Text(
                                text = "@${viewModel.userInfo?.name}",
                                fontFamily = FontFamily(robotoRegular!!),
                                fontSize = 18.sp,
                                color = Color.White
                            )

                        }
                    }

                    if (viewModel.userInfo?.statistics?.followers != null) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.background(
                                        color = Color(0XFFF94C84),
                                        CircleShape
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.follower),
                                        contentDescription = "follower",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(10.dp),
                                        tint = Color.White,
                                    )

                                }
                                Spacer(modifier = Modifier.width(25.dp))
                                Text(
                                    text = "Followers:",
                                    fontFamily = FontFamily(robotoMedium!!),
                                    fontSize = 18.sp,
                                    color = Color.Gray
                                )
                            }
                            Text(
                                text = viewModel.userInfo?.statistics?.followers.toString(),
                                fontFamily = FontFamily(robotoMedium!!),
                                fontSize = 28.sp,
                                color = Color.White,
                                modifier = Modifier
                            )

                        }

                    }


                    if (viewModel.userInfo?.statistics?.following != null) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.background(
                                        color =Color(0XFFF94C84),
                                        CircleShape
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.followings),
                                        contentDescription = "follower",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(10.dp),
                                        tint = Color.White,
                                    )

                                }
                                Spacer(modifier = Modifier.width(25.dp))
                                Text(
                                    text = "Following:",
                                    fontFamily = FontFamily(robotoMedium!!),
                                    fontSize = 18.sp,
                                    color = Color.Gray
                                )

                            }

                            Text(
                                text = viewModel.userInfo?.statistics?.following.toString(),
                                fontFamily = FontFamily(robotoMedium!!),
                                fontSize = 28.sp,
                                color = Color.White,
                                modifier = Modifier
                            )

                        }

                    }



                    if (viewModel.userInfo?.statistics?.posts != null) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.background(
                                        color =Color(0XFFF94C84),
                                        CircleShape
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.posts),
                                        contentDescription = "follower",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(10.dp),
                                        tint = Color.White,
                                    )

                                }
                                Spacer(modifier = Modifier.width(25.dp))
                                Text(
                                    text = "Posts:",
                                    fontFamily = FontFamily(robotoMedium!!),
                                    fontSize = 18.sp,
                                    color = Color.Gray
                                )
                            }
                            Text(
                                text = viewModel.userInfo?.statistics?.posts.toString(),
                                fontFamily = FontFamily(robotoMedium!!),
                                fontSize = 28.sp,
                                color = Color.White,
                                modifier = Modifier
                            )

                        }

                    }
                }
                Column(modifier = Modifier.clickable {
                    viewModel.onEvent(ProfileEvent.OnSignOuClick)

                }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFF474343), RoundedCornerShape(15.dp))
                            .padding(
                                vertical = 15.dp,
                                horizontal = 20.dp
                            )
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.logout),
                                contentDescription = "logout",
                                tint = Color(0XFFF94C84),
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = "Sign Out",
                                fontFamily = FontFamily(robotoMedium!!),
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier
                            )
                        }

                    }

                }

            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    LoadingAnimation(
                        circleColor = Color(0XFFF94C84)
                    )

                }

            }


        }
    }
}