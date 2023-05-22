package com.hanisoft.bestar.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hanisoft.bestar.R
import com.hanisoft.bestar.data.BeStarDataStore
import com.hanisoft.bestar.util.CircularIndeterminateProgressBar
import com.hanisoft.bestar.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val dataStore = BeStarDataStore(context)
    val openLogin = dataStore.getOpenLoginValue.collectAsState(initial = false)
    val mainBold = ResourcesCompat.getFont(context, R.font.arbutus_slab)
    val roboto = ResourcesCompat.getFont(context, R.font.roboto_regular)
    val systemUiController = rememberSystemUiController()


    systemUiController.setSystemBarsColor(
        color = Color(0xFF0A0808),
    )


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

            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {

        Box() {
            Image(
                painter = painterResource(id = R.drawable.login_image2),
                contentDescription = "Logo",
                modifier = Modifier,
                contentScale = ContentScale.FillBounds
            )


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top, modifier = Modifier
                    .padding(top = 200.dp)
            ) {

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Login / Sing Up",
                    color = Color.White,
                    fontFamily = FontFamily(mainBold!!),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 20.dp)
                    ) {
                    Icon(painter = painterResource(id = R.drawable.check_mark), contentDescription = "check mark",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Your account must be public.",
                        color = Color.White,
                        fontFamily = FontFamily(mainBold!!),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                }

                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 20.dp)
                    ) {
                    Icon(painter = painterResource(id = R.drawable.check_mark), contentDescription = "check mark",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Username must be the same as used on tiktok.",
                        color = Color.White,
                        fontFamily = FontFamily(mainBold!!),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                }

                Spacer(modifier = Modifier.height(50.dp))

                if (openLogin.value == true) {
                    AlertDialog(
                        onDismissRequest = {
                            CoroutineScope(Dispatchers.IO).launch {
                                dataStore.saveOpenLoginValue(false)
                            }
                        },
                        backgroundColor = Color(0XFFF94C84),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(250.dp),
                        title = {
                            Text(
                                text = "Is this account belong to you?",
                                color = Color.White,
                                fontFamily = FontFamily(mainBold!!)
                            )

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 30.dp, bottom = 10.dp)
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
                                        .size(90.dp)
                                )
                                viewModel.userInfo?.name?.let { it1 ->
                                    Text(
                                        it1,
                                        color = Color.White,
                                        fontFamily = FontFamily(mainBold)
                                    )
                                }
                            }
                        },
                        buttons = {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                Button(
                                    modifier = Modifier.fillMaxWidth(0.3f),
                                    onClick = {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            dataStore.saveOpenLoginValue(false)
                                        }
                                    }
                                ) {
                                    Text("No")
                                }


                                Button(
                                    modifier = Modifier.fillMaxWidth(0.4f),
                                    onClick = {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            viewModel.onEvent(LoginEvent.OnSelectAccount)
                                            dataStore.saveOpenLoginValue(false)
                                        }
                                    }
                                ) {
                                    Text("Yes")
                                }


                            }
                        }

                    )

                }


                    Box() {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {



                            Text(
                                text = "Write username without @",
                                color = Color.White,
                                fontFamily = FontFamily(mainBold!!),
                                fontSize = 10.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 30.dp)
                            )
                            TextField(
                                value = viewModel.userName, onValueChange = {
                                    viewModel.onEvent(LoginEvent.OnUserNameChange(it))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 10.dp),
                                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    textColor = Color.Black,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                shape = RoundedCornerShape(25.dp),
                                placeholder = { Text(text = "@", color = Color.Gray)}
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = { viewModel.onEvent(LoginEvent.OnLoginClick) }, modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(horizontal = 20.dp),
                                shape = RoundedCornerShape(27.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFF94C84))
                            ) {
                                Text(text = "Login", color = Color.White)
                            }


                        }
                        if (viewModel.loading) {
                            Box(contentAlignment = Alignment.Center) {
                                CircularIndeterminateProgressBar(circleColor = Color(0XFFF94C84))
                            }

                        }

                    }



                Spacer(modifier = Modifier.height(170.dp))
                Text(
                    text = "Have you any question?",
                    color = Color.Gray,
                    fontFamily = FontFamily(roboto!!),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "contact us at: hanisof812@gmail.com",
                    color = Color.Gray,
                    fontFamily = FontFamily(roboto),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )


            }

        }


    }

}


@Preview()
@Composable
fun LoginPreview() {
    LoginScreen()
}




