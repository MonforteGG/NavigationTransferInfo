package com.adm.navigationtransferinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.adm.navigationtransferinfo.ui.theme.NavigationTransferInfoTheme
import com.adm.navigationtransferinfo.ui.theme.Pink40
import com.adm.navigationtransferinfo.ui.theme.Pink80

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTransferInfoTheme {
                val navController = rememberNavController()
                var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
                var textInputUsuario by remember { mutableStateOf("") }
                var newText by remember { mutableStateOf("{newText}") }

                val tabs = listOf(
                    NavItem(
                        label = if (selectedTabIndex == 0) "Home" else "",
                        icon = Icons.Filled.Home,
                        screen = Screen.PantallaUno
                    ),
                    NavItem(
                        label = if (selectedTabIndex == 1) "Setting" else "",
                        icon = Icons.Filled.Settings,
                        screen = Screen.PantallaDos(newText)
                    ),
                    NavItem(
                        label = if (selectedTabIndex == 2) "Profile" else "",
                        icon = Icons.Filled.Person,
                        screen = Screen.PantallaTres
                    )

                )



                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = Pink80,
                                titleContentColor = Pink40,
                            ),
                            title = {
                                Text("Top app bar")
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = Pink80,
                            contentColor = Pink40
                        )
                        {
                            tabs.mapIndexed { index, navItem ->
                                NavigationBarItem(
                                    selected = selectedTabIndex == index,
                                    onClick = {
                                        selectedTabIndex = index
                                        navController.navigate(navItem.screen)
                                    },
                                    icon = {
                                        Icon(
                                            tint = Pink40,
                                            imageVector = navItem.icon,
                                            contentDescription = null
                                        )
                                    },
                                    label = {
                                        Text(
                                            color = Pink40,
                                            text = navItem.label
                                        )
                                    }

                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center

                    ) {

                        NavHost(
                            navController = navController,
                            startDestination = Screen.PantallaUno


                        ) {
                            composable<Screen.PantallaUno> {

                                Column(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(24.dp),
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("PANTALLA 1:")


                                    OutlinedTextField(
                                        value = textInputUsuario,
                                        onValueChange = { textInputUsuario = it },
                                        label = { Text("Introduce el texto:") }
                                    )

                                    TextButton(
                                        onClick = {

                                            navController.navigate(Screen.PantallaDos(textInputUsuario))
                                            selectedTabIndex = 1;


                                        }, colors = ButtonColors(
                                            containerColor = Pink80,
                                            contentColor = Pink40,
                                            disabledContentColor = Pink80,
                                            disabledContainerColor = Pink40
                                        ),
                                        border = BorderStroke(0.5.dp, Color.Black)
                                    ) {
                                        Text("Enviar")
                                    }
                                }

                            }
                            composable<Screen.PantallaDos> { navBackStackEntry ->
                                val texto = navBackStackEntry.toRoute<Screen.PantallaDos>().texto
                                //backStackEntry ->
                                //val texto = backStackEntry.arguments?.getString("texto") ?: ""
                                Text(text = texto)
                            }

                            composable<Screen.PantallaTres> {
                                Text("PANTALLA 3:")
                            }


                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigationTransferInfoTheme {
        Greeting("Android")
    }
}

data class NavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)