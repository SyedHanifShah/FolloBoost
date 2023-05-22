package com.hanisoft.bestar.util


import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.delay
import java.util.*


data class LoginBody(
    val nickname: String,
    val uniqueId: String,
    val posts: Int,
    val follower: Int,
    val following: Int,
    val avtar: String?,
    val date:String
)
data class BoostActivationBody(
    val name: String,
    val followers: Int,
    val following: Int,
    val avatar: String?,
)
data class AddFollowerBody(
    val uniqueId: String
)
data class BoostActivationHeader(
    val token: String,
)


fun isClientIsUserAccount(user: String, followerUser:String): Boolean {
    return user == followerUser
}


@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 25.dp,
    circleColor: Color = MaterialTheme.colors.primary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        circleValues.forEach { value ->
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        color = circleColor,
                        shape = CircleShape
                    )
            )
        }
    }

}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}




@Composable
fun CircularIndeterminateProgressBar(circleColor: Color = MaterialTheme.colors.primary,) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = circleColor
            )
        }

}



fun String.toIntOrZero(): Int {
    var str = this.trim()
    str = if(str.length > 9) str.substring(0,9) else str
    str = str.filter { it.isDigit() }
    return if(str.isNotEmpty()) str.toInt()
    else 0
}

fun String.toLongOrZero(): Long {
    var str = this.trim()
    str = if(str.length > 9) str.substring(0,9) else str
    str = str.filter { it.isDigit() }
    return if(str.isNotEmpty()) str.toLong()
    else 0
}



fun setJoiningTime(timeMillis: Long): String {
        val messageTime = Calendar.getInstance()
        messageTime.timeInMillis = timeMillis
        val now = Calendar.getInstance()
        val strDateFormat = "dd/MM/yyyy"
        val fulMonthFormat = "d MMMM"
        val m = DateUtils.getRelativeTimeSpanString(timeMillis).toString()
        var minute = 0
        if (m.any { it in "1234567890" }) {
            minute = m.filter { it.isDigit() }.toInt()
        }
        return if ((minute < 2) && (m.contains("minute") || m.contains("minutes"))) {
            "Just now"
        } else if ((minute < 60) && (m.contains("minute") || m.contains("minutes"))) {
            m
        } else if (now[Calendar.DATE] == messageTime[Calendar.DATE] &&
            now[Calendar.MONTH] == messageTime[Calendar.MONTH]
            &&
            now[Calendar.YEAR] == messageTime[Calendar.YEAR]
        ) {
            m
        } else if (now[Calendar.DATE] == messageTime[Calendar.DATE] + 1 &&
            now[Calendar.MONTH] == messageTime[Calendar.MONTH]
            &&
            now[Calendar.YEAR] == messageTime[Calendar.YEAR]
        ) {
            "1 day ago"
        } else if (now[Calendar.DATE] in messageTime[Calendar.DATE]..messageTime[Calendar.DATE] + 6 &&
            now[Calendar.MONTH] == messageTime[Calendar.MONTH]
            &&
            now[Calendar.YEAR] == messageTime[Calendar.YEAR]
        ) {
            m
        } else if (now[Calendar.DATE] == messageTime[Calendar.DATE] + 7 &&
            now[Calendar.MONTH] == messageTime[Calendar.MONTH]
            &&
            now[Calendar.YEAR] == messageTime[Calendar.YEAR]
        ) {
            "7 days ago"
        } else if (now[Calendar.DATE] - messageTime[Calendar.DATE] == 1
            &&
            now[Calendar.MONTH] == messageTime[Calendar.MONTH]
            &&
            now[Calendar.YEAR] == messageTime[Calendar.YEAR]
        ) {
            m
        } else if (now[Calendar.YEAR] == messageTime[Calendar.YEAR]) {
            val fmonth = DateFormat.format(fulMonthFormat, messageTime).toString()
            fmonth
        } else {
            DateFormat.format(strDateFormat, messageTime).toString()
        }

}