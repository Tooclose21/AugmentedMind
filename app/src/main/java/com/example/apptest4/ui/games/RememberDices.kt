package com.example.apptest4.ui.games

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.apptest4.ui.gamehelpers.ChooseGameActivity
import com.example.apptest4.ui.gamehelpers.DicesFinishPanel
import com.example.apptest4.controllers.RememberDicesLogic
import com.example.apptest4.ui.theme.AppTest4Theme
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.TrackingFailureReason
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.getDescription
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RememberDices : ComponentActivity() {
    private val logic = RememberDicesLogic()
    // default time when dices are visible
    private var gameMode = 6000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameMode = intent.getIntExtra("gameMode", gameMode)
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

                    // coordinates of object appears
                    var xValue by remember {
                        mutableStateOf(0f)
                    }
                    var yValue by remember {
                        mutableStateOf(0f)
                    }

                    // true if it's possible to generate dices
                    var generate by remember {
                        mutableStateOf(false)
                    }
                    var dicesVisible by remember {
                        mutableStateOf(false)
                    }

                    // list of .glb dices models
                    var models: List<String> by remember {
                        mutableStateOf(listOf())
                    }
                    val dicesNumber = intent.getIntExtra("dicesNumber", 2)
                    var answersList: List<Int> by remember {
                        mutableStateOf(listOf())
                    }

                    var showInstruction by remember {
                        mutableStateOf(true)
                    }
                    var showStart by remember {
                        mutableStateOf(false)
                    }
                    var showAnswers by remember {
                        mutableStateOf(false)
                    }

                    ARScene(modifier = Modifier.fillMaxSize(),
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

                            if (childNodes.isEmpty()) {
                                val detectedPlane = updatedFrame.getUpdatedPlanes()
                                    .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                                if (detectedPlane != null) {
                                    showStart = true
                                }
                            }

                            if (generate && !dicesVisible) {
                                generate = false
                                showStart = false
                                models.forEachIndexed { index, item ->
                                    val position = logic.getPositionAt(index)
                                    xValue = position.x
                                    yValue = position.y
                                    val hitResults = frame?.hitTest(
                                        xValue, yValue
                                    )
                                    hitResults?.firstOrNull {
                                        it.isValid(
                                            depthPoint = false, point = false
                                        )
                                    }?.createAnchorOrNull()?.let { anchor ->
                                        planeRenderer = false
                                        dicesVisible = true
                                        childNodes += createAnchorNode(
                                            engine = engine,
                                            modelLoader = modelLoader,
                                            materialLoader = materialLoader,
                                            anchor = anchor,
                                            "models/$item"
                                        )
                                    }
                                }
                                lifecycleScope.launch {
                                    waitAndRemove {
                                        childNodes.clear()
                                        showAnswers = true
                                    }
                                    answersList = logic.generateAnswers()
                                    showStart = false
                                    showInstruction = false
                                }
                            }
                        })
                    Column(
                        modifier = Modifier
                            .systemBarsPadding()
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        verticalArrangement = Arrangement.spacedBy(40.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ){
                            Button(modifier = Modifier
                                .padding(20.dp),
                                onClick = {
                                startActivity(
                                    Intent(
                                        this@RememberDices, ChooseGameActivity::class.java
                                    )
                                )
                            }) {
                                Text(fontSize = 40.sp, text = "Cancel")
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Button(modifier = Modifier
                                .padding(20.dp),
                                onClick = {
                                    logic.rollDices(dicesNumber)
                                    models = logic.assignDicesNames()
                                    generate = true
                            }) {
                                Text(fontSize = 40.sp, text = "Reload")
                            }
                        }

                        Column(
                            modifier = Modifier
                                .systemBarsPadding()
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (showInstruction) {
                                Text(
                                    modifier = Modifier
                                        .systemBarsPadding()
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 36.sp,
                                    color = Color.White,
                                    text = trackingFailureReason?.getDescription(LocalContext.current)
                                        ?: if (childNodes.isEmpty()) {
                                            "Point your phone down, \nfacing a surface. " +
                                                    "\nMove around slowly \nand wait for surface " +
                                                    "dots \nto appear"
                                        } else {
                                            ""
                                        }
                                )
                            }
                            if (showStart && !showAnswers) {
                                Button(modifier = Modifier
                                    .systemBarsPadding()
                                    .padding(30.dp),
                                    onClick = {
                                        logic.rollDices(dicesNumber)
                                        models = logic.assignDicesNames()
                                        generate = true
                                    }) {
                                    Text(fontSize = 24.sp, text = "Start")
                                }
                            }
                        }
                        if (showAnswers) {
                            Box(
                                modifier = Modifier
                                    .size(360.dp)
                                    .background(
                                        color = Color(0x80FFFFFF),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally),
                                contentAlignment = Alignment.Center

                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "The sum of dots is:",
                                        fontSize = 42.sp,
                                        color = Color.DarkGray
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))

                                    answersList.forEachIndexed { index, answer ->
                                        Button(
                                            onClick = {
                                                startActivity(
                                                    Intent(
                                                        this@RememberDices,
                                                        DicesFinishPanel::class.java
                                                    ).also {
                                                        it.putExtra("answer", answer)
                                                        it.putExtra("gameMode", gameMode)
                                                        it.putExtra("dicesNumber", dicesNumber)
                                                        it.putExtra("correctAnswer", logic.giveCorrectAnswer())
                                                    })
                                            },
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        ) {
                                            Text(answer.toString(), fontSize = 32.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    // function waits given number of seconds and performs an action
    private fun CoroutineScope.waitAndRemove(action: () -> Unit) = launch {
        delay(gameMode.toLong())
        action()
    }

    private fun createAnchorNode(
        engine: Engine,
        modelLoader: ModelLoader,
        materialLoader: MaterialLoader,
        anchor: Anchor,
        model: String
    ): AnchorNode {
        val anchorNode = AnchorNode(engine = engine, anchor = anchor)
        val modelNode = ModelNode(

            modelInstance = modelLoader.createModelInstance(model),

            // Scale to fit in a 0.5 meters cube
            scaleToUnits = 0.15f
        ).apply {
            // Model Node needs to be editable for independent rotation from the anchor rotation
            isEditable = true
            //editableScaleRange = 0.2f..0.75f
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

        return anchorNode
    }
}

