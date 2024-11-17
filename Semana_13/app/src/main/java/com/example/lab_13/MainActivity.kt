package com.example.lab_13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateRect
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab_13.ui.theme.Lab_13Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab_13Theme {
                 Surface(modifier = Modifier.fillMaxSize()) {
                     AnimationExamplesScreen()

                }
            }
        }
    }
}
@Preview
@Composable
fun AnimationExamplesScreen() {
    Column(
    ) {

        ButtonsWhichtAnimation()

    }
}


@Composable
fun MyAnimatedComposable(

) {
    var visible by remember {
        mutableStateOf(true)
    }
    val targetSize by animateDpAsState(
        targetValue = if (visible) 300.dp else 0.dp,
        animationSpec = tween(durationMillis = 2000)
    )
    val targetOffset by animateDpAsState(
        targetValue = if (visible) 0.dp else 100.dp,
        animationSpec = tween(durationMillis = 2000)
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Box(
            modifier = Modifier
                .size(targetSize) 
                .offset(y = targetOffset)
                .background(Color.Blue),
            contentAlignment = Alignment.Center,
        ){
        }
        Box(
            modifier = Modifier.fillMaxSize(),

            ){

            Button(
                modifier = Modifier.align(Alignment.BottomCenter,
                )    ,onClick = { visible = !visible }) {
                Text("Toggle Visibility")
            }
        }
    }


}


@Preview
@Composable
fun AnimatedVisibilityCookbook(
    durationMillis: Int = 250
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        // add border


    ) {
        // [START android_compose_animation_cookbook_visibility]
        var visible by remember {
            mutableStateOf(false)
        }

        val backgroundColor by animateColorAsState(
            targetValue = if (visible) Color.Green else Color.Red,
            animationSpec = tween(durationMillis = durationMillis),
        )
        // Animated visibility will eventually remove the item from the composition once the animation has finished.
        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(
                animationSpec = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
            initialOffsetX = { fullHeight -> -fullHeight }
        ),
        exit = slideOutHorizontally(
            animationSpec = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
            // add animation for background color

            targetOffsetX = { fullWidth -> -fullWidth }
        )

        ) {
            // your composable here
            // [START_EXCLUDE]
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundColor)
            ) {
            }
            // [END_EXCLUDE]
        }
        // [END android_compose_animation_cookbook_visibility]
        Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = {
            visible = !visible
        }) {
            Text("Show/Hide")
        }
    }
}

// definimos los posibles estados de la UI
enum class ScreenState {
    CARGANDO, CONTENIDO, ERROR
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ButtonsWhichtAnimation() {
    var currentState by remember { mutableStateOf(ScreenState.CARGANDO) }


    // con esto cambiamos el contendio cada 2 seg
    LaunchedEffect(currentState) {
        if (currentState == ScreenState.CARGANDO) {
            delay(2000)
            currentState = ScreenState.CONTENIDO
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //
        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                when (initialState to targetState) {
                    ScreenState.CARGANDO to ScreenState.CONTENIDO -> fadeIn(animationSpec = tween(700)) + slideInVertically(initialOffsetY = { -50 }) with fadeOut(animationSpec = tween(500))
                    ScreenState.CONTENIDO to ScreenState.ERROR -> fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.8f) with fadeOut(animationSpec = tween(500))
                    ScreenState.ERROR to ScreenState.CARGANDO -> fadeIn(animationSpec = tween(500)) with fadeOut(animationSpec = tween(300))
                    else -> fadeIn(animationSpec = tween(500)) with fadeOut(animationSpec = tween(500))
                }
            }
        ) { state ->
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = when (state) {
                            ScreenState.CARGANDO -> Color.Yellow
                            ScreenState.CONTENIDO -> Color.Green
                            ScreenState.ERROR -> Color.Red
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (state) {
                        ScreenState.CARGANDO -> "Cargando..."
                        ScreenState.CONTENIDO -> "Contenido cargado con éxito"
                        ScreenState.ERROR -> "Ha ocurrido un error"
                    },
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botones para cambiar entre estados
        Row {
            Button(onClick = { currentState = ScreenState.CARGANDO }) {
                Text("Cargando")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { currentState = ScreenState.CONTENIDO }) {
                Text("Contenido")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { currentState = ScreenState.ERROR }) {
                Text("Error")
            }
        }

        // Botón "Reintentar" que aparece solo en el estado de error
        if (currentState == ScreenState.ERROR) {
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { currentState = ScreenState.CARGANDO }) {
                Text("Reintentar")
            }
        }
    }
}








//@Preview
//@Composable
//fun AnimatedVisibilityCookbook_ModifierAlpha() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // [START android_compose_animation_cookbook_visibility_alpha]
//        var visible by remember {
//            mutableStateOf(true)
//        }
//        val animatedAlpha by animateFloatAsState(
//            targetValue = if (visible) 1.0f else 0f,
//            label = "alpha"
//        )
//        Box(
//            modifier = Modifier
//                .size(200.dp)
//                .graphicsLayer {
//                    alpha = animatedAlpha
//                }
//                .clip(RoundedCornerShape(8.dp))
//                // add funciton of animation color here
//                .background(colorGreen)
//                .align(Alignment.TopCenter)
//        ) {
//        }
//
//        // [END android_compose_animation_cookbook_visibility_alpha]
//        Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = {
//            visible = !visible
//        }) {
//            Text("Show/Hide")
//        }
//    }
//}
//
//@Preview
//@Composable
//fun AnimateBackgroundColor() {
//    var animateBackgroundColor by remember {
//        mutableStateOf(true)
//    }
//    LaunchedEffect(Unit) {
//        animateBackgroundColor = true
//    }
//    // [START android_compose_animate_background_color]
//    val animatedColor by animateColorAsState(
//        if (animateBackgroundColor) colorGreen else colorBlue,
//        label = "color"
//    )
//    Column(
//        modifier = Modifier.drawBehind {
//            drawRect(animatedColor)
//        }
//    ) {
//        // your composable here
//    }
//    // [END android_compose_animate_background_color]
//}
//
//@Preview
//@Composable
//fun AnimatePadding() {
//    Box {
//        // [START android_compose_animation_padding]
//        var toggled by remember {
//            mutableStateOf(false)
//        }
//        val animatedPadding by animateDpAsState(
//            if (toggled) {
//                0.dp
//            } else {
//                20.dp
//            },
//            label = "padding"
//        )
//        Box(
//            modifier = Modifier
//                .aspectRatio(1f)
//                .fillMaxSize()
//                .padding(animatedPadding)
//                .background(Color(0xff53D9A1))
//                .clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = null
//                ) {
//                    toggled = !toggled
//                }
//        )
//        // [END android_compose_animation_padding]
//    }
//}
//
//@Preview
//@Composable
//fun AnimateSizeChange() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // [START android_compose_animation_size_change]
//        var expanded by remember { mutableStateOf(false) }
//        Box(
//            modifier = Modifier
//                .background(colorBlue)
//                .animateContentSize()
//                .height(if (expanded) 400.dp else 200.dp)
//                .fillMaxWidth()
//                .clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = null
//                ) {
//                    expanded = !expanded
//                }
//
//        ) {
//        }
//        // [END android_compose_animation_size_change]
//    }
//}
//
//@Preview
//@Composable
//fun AnimateOffset() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // [START android_compose_animation_offset_change]
//        var moved by remember { mutableStateOf(false) }
//        val pxToMove = with(LocalDensity.current) {
//            100.dp.toPx().roundToInt()
//        }
//        val offset by animateIntOffsetAsState(
//            targetValue = if (moved) {
//                IntOffset(pxToMove, pxToMove)
//            } else {
//                IntOffset.Zero
//            },
//            label = "offset"
//        )
//
//        Box(
//            modifier = Modifier
//                .offset {
//                    offset
//                }
//                .background(colorBlue)
//                .size(100.dp)
//                .clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = null
//                ) {
//                    moved = !moved
//                }
//        )
//        // [END android_compose_animation_offset_change]
//    }
//}
//
//@Preview
//@Composable
//fun AnimateBetweenComposableDestinations() {
//    // [START android_compose_animate_destinations]
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController, startDestination = "landing",
//        enterTransition = { EnterTransition.None },
//        exitTransition = { ExitTransition.None }
//    ) {
//        composable("landing") {
//            ScreenLanding(
//                // [START_EXCLUDE]
//                onItemClicked = {
//                    navController.navigate("detail/${URLEncoder.encode(it.toString())}")
//                }
//                // [END_EXCLUDE]
//            )
//        }
//        composable(
//            "detail/{photoUrl}",
//            arguments = listOf(navArgument("photoUrl") { type = NavType.StringType }),
//            enterTransition = {
//                fadeIn(
//                    animationSpec = tween(
//                        300, easing = LinearEasing
//                    )
//                ) + slideIntoContainer(
//                    animationSpec = tween(300, easing = EaseIn),
//                    towards = AnimatedContentTransitionScope.SlideDirection.Start
//                )
//            },
//            exitTransition = {
//                fadeOut(
//                    animationSpec = tween(
//                        300, easing = LinearEasing
//                    )
//                ) + slideOutOfContainer(
//                    animationSpec = tween(300, easing = EaseOut),
//                    towards = AnimatedContentTransitionScope.SlideDirection.End
//                )
//            }
//        ) { backStackEntry ->
//            ScreenDetails(
//                // [START_EXCLUDE]
//                photo = URLDecoder.decode(backStackEntry.arguments!!.getString("photoUrl")!!),
//                onBackClicked = {
//                    navController.popBackStack()
//                }
//                // [END_EXCLUDE]
//            )
//        }
//    }
//    // [END android_compose_animate_destinations]
//}
//
//@Composable
//fun ScreenLanding(onItemClicked: () -> Unit) {
//    TODO("Not yet implemented")
//}
//
//@Composable
//fun ScreenDetails(photo: String?, onBackClicked: () -> Boolean) {
//
//
//}
//
//@Preview
//@Composable
//fun AnimateSizeChange_Specs() {
//    Row(modifier = Modifier.fillMaxSize()) {
//        var expanded by remember { mutableStateOf(false) }
//        Column(
//            modifier = Modifier
//                .padding(8.dp)
//                .weight(1f)
//        ) {
//            Text("No spec set")
//            Box(
//                modifier = Modifier
//                    .background(colorBlue)
//                    .animateContentSize()
//                    .height(if (expanded) 300.dp else 200.dp)
//                    .fillMaxSize()
//                    .clickable(
//                        interactionSource = remember { MutableInteractionSource() },
//                        indication = null
//                    ) {
//                        expanded = !expanded
//                    }
//
//            ) {
//            }
//        }
//        Column(
//            modifier = Modifier
//                .padding(8.dp)
//                .weight(1f)
//        ) {
//            Text("Custom spec")
//            // [START android_compose_animation_size_change_spec]
//            Box(
//                modifier = Modifier
//                    .background(colorBlue)
//                    .animateContentSize(
//                        spring(
//                            stiffness = Spring.StiffnessLow,
//                            dampingRatio = Spring.DampingRatioHighBouncy
//                        )
//                    )
//                    .height(if (expanded) 300.dp else 200.dp)
//                    .fillMaxSize()
//                    .clickable(
//                        interactionSource = remember { MutableInteractionSource() },
//                        indication = null
//                    ) {
//                        expanded = !expanded
//                    }
//
//            ) {
//            }
//            // [END android_compose_animation_size_change_spec]
//        }
//    }
//}
//
//@Preview
//@Composable
//fun SmoothAnimateText() {
//    // [START android_compose_animation_cookbook_text]
//    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
//    val scale by infiniteTransition.animateFloat(
//        initialValue = 1f,
//        targetValue = 8f,
//        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
//        label = "scale"
//    )
//    Box(modifier = Modifier.fillMaxSize()) {
//        Text(
//            text = "Hello",
//            modifier = Modifier
//                .graphicsLayer {
//                    scaleX = scale
//                    scaleY = scale
//                    transformOrigin = TransformOrigin.Center
//                }
//                .align(Alignment.Center),
//            // Text composable does not take TextMotion as a parameter.
//            // Provide it via style argument but make sure that we are copying from current theme
//            style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
//        )
//    }
//
//    // [END android_compose_animation_cookbook_text]
//}
//
//@Preview
//@Composable
//fun AnimateTextColor() {
//    Box(modifier = Modifier.fillMaxSize()) {
//        // [START android_compose_animation_cookbook_text_color]
//        val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
//        val animatedColor by infiniteTransition.animateColor(
//            initialValue = Color(0xFF60DDAD),
//            targetValue = Color(0xFF4285F4),
//            animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
//            label = "color"
//        )
//
//        BasicText(
//            text = "Hello Compose",
//            color = {
//                animatedColor
//            },
//            // [START_EXCLUDE]
//            style = MaterialTheme.typography.displayLarge,
//            modifier = Modifier
//                .align(Alignment.Center)
//                .padding(16.dp)
//            // [END_EXCLUDE]
//        )
//        // [END android_compose_animation_cookbook_text_color]
//    }
//}
//
//@Preview
//@Composable
//fun InfinitelyRepeatable() {
//    // [START android_compose_animation_infinitely_repeating]
//    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
//    val color by infiniteTransition.animateColor(
//        initialValue = Color.Green,
//        targetValue = Color.Blue,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1000, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "color"
//    )
//    Column(
//        modifier = Modifier.drawBehind {
//            drawRect(color)
//        }
//    ) {
//        // your composable here
//    }
//    // [END android_compose_animation_infinitely_repeating]
//}
//
//@Preview
//@Composable
//fun ConcurrentAnimatable() {
//    // [START android_compose_animation_on_launch]
//    val alphaAnimation = remember {
//        Animatable(0f)
//    }
//    LaunchedEffect(Unit) {
//        alphaAnimation.animateTo(1f)
//    }
//    Box(
//        modifier = Modifier.graphicsLayer {
//            alpha = alphaAnimation.value
//        }
//    )
//    // [END android_compose_animation_on_launch]
//}
//
//@Preview
//@Composable
//fun SequentialAnimations() {
//    // [START android_compose_animation_sequential]
//    val alphaAnimation = remember { Animatable(0f) }
//    val yAnimation = remember { Animatable(0f) }
//
//    LaunchedEffect("animationKey") {
//        alphaAnimation.animateTo(1f)
//        yAnimation.animateTo(100f)
//        yAnimation.animateTo(500f, animationSpec = tween(100))
//    }
//    // [END android_compose_animation_sequential]
//}
//
//@Preview
//@Composable
//fun ConcurrentAnimations() {
//    // [START android_compose_animation_concurrent]
//    val alphaAnimation = remember { Animatable(0f) }
//    val yAnimation = remember { Animatable(0f) }
//
//    LaunchedEffect("animationKey") {
//        launch {
//            alphaAnimation.animateTo(1f)
//        }
//        launch {
//            yAnimation.animateTo(100f)
//        }
//    }
//    // [END android_compose_animation_concurrent]
//}
//enum class BoxState {
//    Collapsed,
//    Expanded
//}
//@Preview
//@Composable
//fun TransitionExampleConcurrent() {
//    // [START android_compose_concurrent_transition]
//    var currentState by remember { mutableStateOf(BoxState.Collapsed) }
//    val transition = updateTransition(currentState, label = "transition")
//
//    val rect by transition.animateRect(label = "rect") { state ->
//        when (state) {
//            BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
//            BoxState.Expanded -> Rect(100f, 100f, 300f, 300f)
//        }
//    }
//    val borderWidth by transition.animateDp(label = "borderWidth") { state ->
//        when (state) {
//            BoxState.Collapsed -> 1.dp
//            BoxState.Expanded -> 0.dp
//        }
//    }
//    // [END android_compose_concurrent_transition]
//}
//
//@Preview
//@Composable
//fun AnimateElevation() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        // [START android_compose_animation_cookbook_elevation]
//        val mutableInteractionSource = remember {
//            MutableInteractionSource()
//        }
//        val pressed = mutableInteractionSource.collectIsPressedAsState()
//        val elevation = animateDpAsState(
//            targetValue = if (pressed.value) {
//                32.dp
//            } else {
//                8.dp
//            },
//            label = "elevation"
//        )
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .align(Alignment.Center)
//                .graphicsLayer {
//                    this.shadowElevation = elevation.value.toPx()
//                }
//                .clickable(interactionSource = mutableInteractionSource, indication = null) {
//                }
//                .background(colorGreen)
//        ) {
//        }
//        // [END android_compose_animation_cookbook_elevation]
//    }
//}

//@Preview
//@Composable
//fun AnimatedContentExampleSwitch() {
//    // [START android_compose_animation_cookbook_animated_content]
//    var state by remember {
//        mutableStateOf(UiState.Loading)
//    }
//    AnimatedContent(
//        state,
//        transitionSpec = {
//            fadeIn(
//                animationSpec = tween(3000)
//            ) togetherWith fadeOut(animationSpec = tween(3000))
//        },
//        modifier = Modifier.clickable(
//            interactionSource = remember { MutableInteractionSource() },
//            indication = null
//        ) {
//            state = when (state) {
//                UiState.Loading -> UiState.Loaded
//                UiState.Loaded -> UiState.Error
//                UiState.Error -> UiState.Loading
//            }
//        },
//        label = "Animated Content"
//    ) { targetState ->
//        when (targetState) {
//            UiState.Loading -> {
//                LoadingScreen()
//            }
//            UiState.Loaded -> {
//                LoadedScreen()
//            }
//            UiState.Error -> {
//                ErrorScreen()
//            }
//        }
//    }
//    // [END android_compose_animation_cookbook_animated_content]
//}
//
//@Composable
//private fun ErrorScreen() {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // [START_EXCLUDE]
//        Text("Error", fontSize = 18.sp)
//        // [END_EXCLUDE]
//    }
//}
//
//@Composable
//private fun LoadedScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // [START_EXCLUDE]
//        Text("Loaded", fontSize = 18.sp)
//        Image(
//            painterResource(id = R.drawable.ic_launcher_background),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .padding(16.dp)
//                .clip(
//                    RoundedCornerShape(16.dp)
//                ),
//            contentDescription = "dog",
//            contentScale = ContentScale.Crop
//        )
//        // [END_EXCLUDE]
//    }
//}
//
//@Composable
//private fun LoadingScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//
//    ) {
//        CircularProgressIndicator(modifier = Modifier.size(48.dp))
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("Loading", fontSize = 18.sp)
//    }
//}
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Preview
//@Composable
//fun AnimationLayout() {
//    // [START android_compose_animation_layout_offset]
//    var toggled by remember {
//        mutableStateOf(false)
//    }
//    val interactionSource = remember {
//        MutableInteractionSource()
//    }
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxSize()
//            .clickable(indication = null, interactionSource = interactionSource) {
//                toggled = !toggled
//            }
//    ) {
//        val offsetTarget = if (toggled) {
//            IntOffset(150, 150)
//        } else {
//            IntOffset.Zero
//        }
//        val offset = animateIntOffsetAsState(
//            targetValue = offsetTarget, label = "offset"
//        )
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .background(colorBlue)
//        )
//        Box(
//            modifier = Modifier
//                .layout { measurable, constraints ->
//                    val offsetValue = if (isLookingAhead) offsetTarget else offset.value
//                    val placeable = measurable.measure(constraints)
//                    layout(placeable.width + offsetValue.x, placeable.height + offsetValue.y) {
//                        placeable.placeRelative(offsetValue)
//                    }
//                }
//                .size(100.dp)
//                .background(colorGreen)
//        )
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .background(colorBlue)
//        )
//    }
//    // [END android_compose_animation_layout_offset]
//}
//
//@Preview
//@Composable
//fun AnimateAlignment() {
//    // [START android_compose_animate_item_placement]
//    var toggled by remember {
//        mutableStateOf(false)
//    }
//    val interactionSource = remember {
//        MutableInteractionSource()
//    }
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxSize()
//            .clickable(indication = null, interactionSource = interactionSource) {
//                toggled = !toggled
//            }
//    ) {
//
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .background(colorBlue)
//        )
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .background(colorGreen)
//        )
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .background(colorBlue)
//        )
//    }
//    // [END android_compose_animate_item_placement]
//}
//
//enum class UiState {
//    Loading,
//    Loaded,
//    Error
//}
//
//val colorGreen = Color(0xFF53D9A1)
//val colorBlue = Color(0xFF4FC3F7)
//
//@Preview
//@Composable
//fun AnimationLayoutIndividualItem() {
//    var toggled by remember {
//        mutableStateOf(false)
//    }
//    val interactionSource = remember {
//        MutableInteractionSource()
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .clickable(indication = null, interactionSource = interactionSource) {
//                toggled = !toggled
//            }
//    ) {
//        val offset = animateIntOffsetAsState(
//            targetValue = if (toggled) {
//                IntOffset(150, 150)
//            } else {
//                IntOffset.Zero
//            },
//            label = "offset"
//        )
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .background(colorBlue)
//        )
//        Box(
//            modifier = Modifier
//                .layout { measurable, constraints ->
//                    val placeable = measurable.measure(constraints)
//                    layout(placeable.width + offset.value.x, placeable.height + offset.value.y) {
//                        placeable.placeRelative(offset.value)
//                    }
//                }
//                .size(100.dp)
//                .background(colorGreen)
//        )
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .background(colorBlue)
//        )
//    }
//}
//
