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
import com.google.gson.GsonBuilder
import org.json.JSONObject

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

        data class Theme(
            val primaryColor: String,
            val backgroundColor: String,
            val textColor: String,
            val borderRadius: String
        )

        data class Configuration(
            val theme: Theme
        )

        val config = Configuration(
            theme = Theme(
                primaryColor = "#FF0000",
                backgroundColor = "#000000",
                textColor = "#FFFFFF",
                borderRadius = "10px"
            )
        )

        val settings = AlloySettings(
            apiKey = "3c6b3646-85d2-419a-a168-3aca019d93a8",
            production = false,
            journeyToken = "J-siYOQbXkJFcTOwvXhvHp",
            journeyApplicationToken = "JA-HKzHsS2VkDx0evHWu644",
            customStyle = config,
            entityToken = "P-Rs1ICuCBLSihbETsNvdQ",
            isSingleEntity = true,
            appUrl =  "https://corekube-dev-alloysdk.app.alloy.com/",
            apiUrl =  "https://corekube-dev-alloysdk.api.alloy.com/",
        )

        data class Configuration(
            val theme: Theme
        )

        val config = Configuration(
            theme = Theme(
                primaryColor = "#FF0000",
                backgroundColor = "#000000",
                textColor = "#FFFFFF",
                borderRadius = "10px"
            )
        )

        val settings = AlloySettings(
            apiKey = "3c6b3646-85d2-419a-a168-3aca019d93a8",
            production = false,
            journeyToken = "J-siYOQbXkJFcTOwvXhvHp",
            journeyApplicationToken = "JA-HKzHsS2VkDx0evHWu644",
            customStyle = config,
            entityToken = "P-Rs1ICuCBLSihbETsNvdQ",
            isSingleEntity = true,
            appUrl =  "https://corekube-dev-alloysdk.app.alloy.com/",
            apiUrl =  "https://corekube-dev-alloysdk.api.alloy.com/",
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

            }

            override fun onDone(result: JSONObject) {
                Log.d("AlloyDemo", "onDone ---->  $result")
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
