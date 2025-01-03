package com.example.apptest4.ui.games

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.apptest4.ui.gamehelpers.FinishedGameActivity
import com.example.apptest4.R
import com.example.apptest4.helpers.generateNumbersFloat
import com.example.apptest4.helpers.generateNumbersInt
import com.example.apptest4.ui.theme.AppTest4Theme
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.TrackingFailureReason
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.getDescription
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.node.Node
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class CatchTheBear : ComponentActivity() {
    private val kModelFile = "models/icebear2.glb"
    @SuppressLint("MutableCollectionMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameTime = intent.getIntExtra("gameTime", 10000)
        val bearAppearanceTime = mutableMapOf<Node, Long>()
        setContent {
            AppTest4Theme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // rendering utilities
                    val engine = rememberEngine()
                    val modelLoader = rememberModelLoader(engine)
                    val materialLoader = rememberMaterialLoader(engine)
                    val cameraNode = rememberARCameraNode(engine)
                    val childNodes = rememberNodes()
                    val view = rememberView(engine)
                    val collisionSystem = rememberCollisionSystem(view)
                    var planeRenderer by remember { mutableStateOf(true) }
                    var frame by remember { mutableStateOf<Frame?>(null) }
                    var flag by remember {
                        mutableStateOf(false)
                    }
                    val deathOfBear by remember {
                        mutableStateOf(mutableListOf<Long>())
                    }

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

                    lifecycleScope.waitAndRemove(action = {
                        childNodes.clear()
                        deathOfBear.forEach{
                            Log.d("TIME", it.toString())
                        }
                        startActivity(
                            Intent(
                                this@CatchTheBear,
                                FinishedGameActivity::class.java
                            ).also {
                                it.putExtra("gamePoints", gamePoints)
                                it.putExtra("deathOfBear", deathOfBear.toLongArray())
                                it.putExtra("gameTime", gameTime)
                            })
                    }, gameTime = gameTime, flag, setFlag = {flag = it})

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
                            if (counter >= timeTrigger && maxRender < 10) {
                                timeTrigger = generateNumbersInt(200, 500)
                                counter = 0
                                xValue = generateNumbersFloat(100f, 700f)
                                yValue = generateNumbersFloat(200f, 1200f)
                                val hitResults = frame?.hitTest(
                                    xValue,
                                    yValue
                                )
                                hitResults?.firstOrNull {
                                    it.isValid(
                                        depthPoint = false, point = false
                                    )
                                }?.createAnchorOrNull()?.let { anchor ->
                                    planeRenderer = false
                                    maxRender++
                                    val createAnchorNode = createAnchorNode(
                                        engine = engine,
                                        modelLoader = modelLoader,
                                        materialLoader = materialLoader,
                                        anchor = anchor,
                                        onLongPress = {
                                            gamePoints++
                                            maxRender--
                                            childNodes.remove(it)
                                            deathOfBear.add(Calendar.getInstance().timeInMillis - bearAppearanceTime[it]!!)
                                            Log.d("TIME", (Calendar.getInstance().timeInMillis - bearAppearanceTime[it]!!).toString())
                                            Log.d("TIME", (bearAppearanceTime[it]!!).toString())
                                        }
                                    )
                                    childNodes += createAnchorNode
                                    bearAppearanceTime.put(createAnchorNode, Calendar.getInstance().timeInMillis)
                                }
                                //Log.d("KONIEC", "koniec")
                            } else if (counter < timeTrigger) {
                                counter++
                            }
                        },
                    )
                    Column(
                        modifier = Modifier
                            .systemBarsPadding()
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        verticalArrangement = Arrangement.spacedBy(40.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .systemBarsPadding()
                                .fillMaxWidth()
                        ) {
                            Button(modifier = Modifier
                                .systemBarsPadding()
                                .padding(30.dp),
                                onClick = {
                                    startActivity(Intent(
                                        this@CatchTheBear,
                                        FinishedGameActivity::class.java
                                    ).also {
                                        it.putExtra("gamePoints", gamePoints)
                                        it.putExtra("deathOfBear", deathOfBear.toLongArray())
                                        it.putExtra("gameTime", gameTime)
                                    })
                                }) {
                                Text(fontSize = 40.sp, text = "Finish")
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                modifier = Modifier
                                    .systemBarsPadding()
                                    .padding(32.dp),
                                textAlign = TextAlign.Right,
                                fontSize = 32.sp,
                                color = Color.White,
                                text = "Points: $gamePoints"
                            )
                        }
                        Text(
                            modifier = Modifier
                                .systemBarsPadding()
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 32.sp,
                            color = Color.White,
                            text = trackingFailureReason?.getDescription(LocalContext.current)
                                ?: if (childNodes.isEmpty()) {
                                    stringResource(R.string.point_your_phone_down)
                                } else {
                                    ""
                                }
                        )
                    }
                }
            }
        }
    }

    // function waits given number of seconds and performs an action
    private fun CoroutineScope.waitAndRemove(
        action: () -> Unit, gameTime: Int, flag: Boolean,
        setFlag: (Boolean) -> Unit
    ) = launch {
        if (flag) {return@launch}
        Toast.makeText(this@CatchTheBear, "start", Toast.LENGTH_SHORT).show()
        setFlag(true)
        delay(gameTime.toLong())
        action()
    }

    fun createAnchorNode(
        engine: Engine, modelLoader: ModelLoader, materialLoader: MaterialLoader, anchor: Anchor,
        onLongPress: (AnchorNode) -> Unit
    ): AnchorNode {
        val anchorNode = AnchorNode(engine = engine, anchor = anchor)
        val modelNode = ModelNode(
            modelInstance = modelLoader.createModelInstance(kModelFile),
            // Scale to fit in a 0.5 meters cube

            scaleToUnits = generateNumbersFloat(0.1f, 0.5f)
        ).apply {
            // Model Node needs to be editable for independent rotation from the anchor rotation
            isEditable = false
            editableScaleRange = 0.2f..0.75f
        }
        val boundingBoxNode = CubeNode(
            engine,
            size = modelNode.extents,
            center = modelNode.center,
            materialInstance = materialLoader.createColorInstance(
                Color.White.copy(alpha = 0.5f)
            )
        ).apply {
            isVisible = false
        }
        modelNode.addChildNode(boundingBoxNode)
        anchorNode.addChildNode(modelNode)

        listOf(modelNode, anchorNode).forEach {
            it.onEditingChanged = { editingTransforms ->
                boundingBoxNode.isVisible = editingTransforms.isNotEmpty()
            }
        }
        modelNode.onSingleTapConfirmed = {
            onLongPress(anchorNode)
            true
        }
        return anchorNode
    }

}
