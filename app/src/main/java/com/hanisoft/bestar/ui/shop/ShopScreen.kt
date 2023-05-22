package com.hanisoft.bestar.ui.shop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.hanisoft.bestar.R
import com.hanisoft.bestar.util.UiEvent

@Composable
fun ShopScreen(
    viewModel: ShopViewModel = hiltViewModel(),
    onPopBackStack:()-> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val titleFont = ResourcesCompat.getFont(context, R.font.pacifico_regular)
    val mainBold = ResourcesCompat.getFont(context, R.font.arbutus_slab)
    val robotoMedium = ResourcesCompat.getFont(context, R.font.arbutus_slab)
    val robotoRegular = ResourcesCompat.getFont(context, R.font.roboto_regular)
    val packages = viewModel.packagesList


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }
                is UiEvent.PopBackStack -> onPopBackStack()

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
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "back",
                        modifier =Modifier.clickable {viewModel.onEvent(ShopEvent.OnbackClick) }
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {

                        Text(
                            text = "Buy Coins",
                            modifier = Modifier,
                            color = Color.White,
                            fontFamily = FontFamily(mainBold!!),
                            fontSize = 18.sp
                        )
                    }

                },
                actions = {},
                backgroundColor = Color(0XFFF94C84)
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        ) {

            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 30.dp)) {
                Text(
                    text = "Coins",
                    modifier = Modifier,
                    color = Color.White,
                    fontFamily = FontFamily(robotoMedium!!),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "You can buy the most suitable coins for you.",
                    modifier = Modifier,
                    color = Color.LightGray,
                    fontFamily = FontFamily(robotoMedium!!),
                    fontSize = 15.sp
                )

            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 50.dp)
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                    items(packages){
                        if (it != null) {
                            PackageSingleItem(it)
                        }
                    }
                })

            }


        }

    }

}

@Composable
fun PackageSingleItem(packageItem :com.hanisoft.bestar.domain.item.Package) {
    val context = LocalContext.current
    val mainBold = ResourcesCompat.getFont(context, R.font.arbutus_slab)



    Card(
        backgroundColor = Color(0xFF4E4D4D),
        modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 10.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp,
        border = BorderStroke(0.5.dp, Color(0XFFF94C84))
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.coin), contentDescription = "coins",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = packageItem.coins, color = Color.White, fontFamily = FontFamily(mainBold!!),
                fontSize = 20.sp
            )
            Text(
                text = packageItem.name,
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Divider(color = Color.Gray, modifier = Modifier.width(60.dp))

            Text(
                text = "$${packageItem.price}",
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.LightGray, fontFamily = FontFamily(mainBold!!),
                fontSize = 15.sp

            )

        }


    }


}
