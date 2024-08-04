package com.example.apptest4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.apptest4.helpers.generateNumbersInt
import com.example.apptest4.ui.theme.AppTest4Theme
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.TrackingFailureReason
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberView

class RememberDices : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTest4Theme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val engine = rememberEngine()
                    val modelLoader = rememberModelLoader(engine)
                    val materialLoader = rememberMaterialLoader(engine)
                    val cameraNode = rememberARCameraNode(engine)
                    val childNodes = rememberNodes()
                    val view = rememberView(engine)
                    val collisionSystem = rememberCollisionSystem(view)
                    var planeRenderer by remember { mutableStateOf(true) }
                    var frame by remember { mutableStateOf<Frame?>(null) }

                    // debug variables
                    var trackingFailureReason by remember {
                        mutableStateOf<TrackingFailureReason?>(null)
                    }

                    // object rendering conditions
                    // counter - counts frames, when it's greater than timeTrigger
                    // object can appear, counter and timeTrigger is reevaluated
                    var counter by remember {
                        mutableStateOf(0)
                    }
                    var timeTrigger by remember {
                        mutableStateOf(generateNumbersInt(800, 1200))
                    }
                    // max number of objects rendered at the same time
                    var maxRender by remember {
                        mutableStateOf(0)
                    }

                    // coordinates of object appears
                    var xValue by remember {
                        mutableStateOf(0f)
                    }
                    var yValue by remember {
                        mutableStateOf(0f)
                    }

                    var gamePoints by remember {
                        mutableStateOf(0)
                    }

                    ARScene(
                        modifier = Modifier.fillMaxSize(),
                        childNodes = childNodes,
                        engine = engine,
                        view = view,
                        modelLoader = modelLoader,
                        collisionSystem = collisionSystem,
                        sessionConfiguration = { session, config ->
                            config.depthMode =
                                when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                                    true -> Config.DepthMode.AUTOMATIC
                                    else -> Config.DepthMode.DISABLED
                                }
                            config.instantPlacementMode = Config.InstantPlacementMode.LOCAL_Y_UP
                            config.lightEstimationMode =
                                Config.LightEstimationMode.ENVIRONMENTAL_HDR
                        },
                        cameraNode = cameraNode,
                        planeRenderer = planeRenderer,
                        onTrackingFailureChanged = {
                            trackingFailureReason = it
                        },
                        onSessionUpdated = { session, updatedFrame ->
                            frame = updatedFrame

                        }
                    )
                }
            }
        }
    }
}
