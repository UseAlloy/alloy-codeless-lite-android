package co.alloy.codelesssdklite.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.alloy.codelesssdklite.Alloy
import co.alloy.codelesssdklite.AlloySettings
import co.alloy.codelesssdklite.example.ui.theme.AlloyCodelessSdkLiteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val entityDataPerson = AlloySettings.Entity.EntityData(firstName = "John", lastName = "Random6")
        val entityPerson = AlloySettings.Entity(entityData = entityDataPerson, entityType = "person", branchName = "persons")
        val entityDataPerson2 = AlloySettings.Entity.EntityData(firstName = "Mary", lastName = "Random6 Random6")
        val entityPerson2 = AlloySettings.Entity(entityData = entityDataPerson2, entityType = "person", branchName = "persons")

        val journeyData = AlloySettings.JourneyData(
            entities = listOf(entityPerson, entityPerson2),
            doAwaitAdditionalEntities = false,
            externalGroupId = null,
            externalProductId = null,
        )

//        val settings = AlloySettings(
//            apiKey = "9ca83767-f213-4aaf-bc1b-1ed0a89eaf23",
//            production = false,
//            isNext = true,
//            journeyToken = "J-UMEhLDP3p759425pz1uP",
//            journeyApplicationToken = "JA-eJ8aIOG5UhmL0liGeAlz",
//            journeyData = journeyData,
//        )

        val settings = AlloySettings(
            apiKey = "22501763-79c3-4f8d-a069-ce99afad16d5",
            journeyToken =  "J-UMEhLDP3p759425pz1uP",
            journeyApplicationToken = "JA-V4okGJmdcCnaPVneWt5Y",
            entityToken = "P-4dutshcn5WOyFTJBMqMD",
            production = false,
        )

        Alloy.listener = object : Alloy.Listener {
            override fun onCancelled() {
                Log.d("AlloyDemo", "onCancelled")
            }

            override fun onDenied() {
                Log.d("AlloyDemo", "onDenied")
            }

            override fun onManualReview() {
                Log.d("AlloyDemo", "onManualReview")
            }

            override fun onSuccess() {
                Log.d("AlloyDemo", "onSuccess")
            }

            override fun journeyApplicationTokenCreated(token: String) {
                Log.d("AlloyDemo", "journeyApplicationTokenCreated: $token")
            }

            override fun gotError(error: String) {
                Log.d("AlloyDemo", "gotError: $error")
            }
        }

        setContent {
            AlloyCodelessSdkLiteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {
                                    Alloy.start(this@MainActivity, settings)
                                }
                            ) {
                                Text(text = stringResource(R.string.main_start_sdk))
                            }

                            Button(
                                onClick = {
                                    Alloy.createApplication(this@MainActivity, settings)
                                }
                            ) {
                                Text(text = stringResource(R.string.main_create_journey_application))
                            }
                        }
                    }
                }
            }
        }
    }
}
