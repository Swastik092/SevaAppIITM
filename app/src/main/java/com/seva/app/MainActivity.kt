package com.seva.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.seva.app.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SevaAppTheme {
                var currentScreen by rememberSaveable { mutableStateOf("onboarding") }
                var selectedState by rememberSaveable { mutableStateOf("Maharashtra") }
                var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }
                
                if (currentScreen == "onboarding") {
                    OnboardingScreen(onGetStartedClick = {
                        currentScreen = "home"
                    })
                } else {
                    Scaffold(
                        bottomBar = {
                            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                                NavigationBarItem(
                                    selected = currentScreen == "home",
                                    onClick = { currentScreen = "home" },
                                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                    label = { Text("Home") },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = SevaOrange,
                                        selectedTextColor = SevaOrange,
                                        indicatorColor = SevaOrange.copy(alpha = 0.1f)
                                    )
                                )
                                NavigationBarItem(
                                    selected = currentScreen == "categories",
                                    onClick = { currentScreen = "categories" },
                                    icon = { Icon(Icons.Default.Category, contentDescription = "Categories") },
                                    label = { Text("Categories") },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = SevaOrange,
                                        selectedTextColor = SevaOrange,
                                        indicatorColor = SevaOrange.copy(alpha = 0.1f)
                                    )
                                )
                                NavigationBarItem(
                                    selected = currentScreen == "profile",
                                    onClick = { currentScreen = "profile" },
                                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                                    label = { Text("Profile") },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = SevaOrange,
                                        selectedTextColor = SevaOrange,
                                        indicatorColor = SevaOrange.copy(alpha = 0.1f)
                                    )
                                )
                            }
                        }
                    ) { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            when (currentScreen) {
                                "home" -> {
                                    HomeScreen(
                                        selectedState = selectedState,
                                        onStateChange = { selectedState = it },
                                        onCategoryClick = { category ->
                                            selectedCategory = category
                                            currentScreen = "services"
                                        },
                                        onViewAllClick = {
                                            currentScreen = "categories"
                                        }
                                    )
                                }
                                "services" -> {
                                    ServicesScreen(
                                        initialCategory = selectedCategory,
                                        initialState = selectedState,
                                        onBackClick = {
                                            currentScreen = "home"
                                        }
                                    )
                                }
                                "categories" -> {
                                    CategoriesScreen(onCategoryClick = { category ->
                                        selectedCategory = category
                                        currentScreen = "services"
                                    })
                                }
                                "profile" -> {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text("Profile Screen Coming Soon", style = MaterialTheme.typography.titleLarge, color = SevaDarkBlue)
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

data class Service(
    val name: String,
    val category: String,
    val state: String,
    val url: String
)

val services = listOf(
    // Maharashtra
    Service("Maharashtra Emergency Services", "Emergency Services", "Maharashtra", "https://www.maharashtra.gov.in/"),
    Service("Maharashtra Women Safety", "Women Safety", "Maharashtra", "https://mumbaipolice.gov.in/WomenSafety"),
    Service("Maharashtra Public Safety", "Public Safety", "Maharashtra", "https://www.maharashtrapolice.gov.in/"),
    Service("Maharashtra Environmental Services", "Environmental & Pollution", "Maharashtra", "https://www.mpcb.gov.in/"),
    Service("Maharashtra Traffic Services", "Traffic & Transport", "Maharashtra", "https://mahatranscom.in/"),
    
    // Karnataka
    Service("Karnataka Emergency Services", "Emergency Services", "Karnataka", "https://www.karnataka.gov.in/"),
    Service("Karnataka Women Safety", "Women Safety", "Karnataka", "https://ksp.karnataka.gov.in/page/Women+Safety/en"),
    Service("Karnataka Public Safety", "Public Safety", "Karnataka", "https://ksp.karnataka.gov.in/"),
    Service("Karnataka Environmental Services", "Environmental & Pollution", "Karnataka", "https://kspcb.karnataka.gov.in/"),
    Service("Karnataka Traffic Services", "Traffic & Transport", "Karnataka", "https://transport.karnataka.gov.in/"),
    
    // Delhi
    Service("Delhi Emergency Services", "Emergency Services", "Delhi", "https://delhi.gov.in/"),
    Service("Delhi Women Safety", "Women Safety", "Delhi", "https://www.delhipolice.nic.in/"),
    Service("Delhi Public Safety", "Public Safety", "Delhi", "https://www.delhipolice.nic.in/"),
    Service("Delhi Environmental Services", "Environmental & Pollution", "Delhi", "https://dpcc.delhigovt.nic.in/"),
    Service("Delhi Traffic Services", "Traffic & Transport", "Delhi", "https://traffic.delhipolice.nic.in/")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SevaAppScreen() {
    val context = LocalContext.current
    val states = listOf("Maharashtra", "Karnataka", "Delhi")

    var expanded by remember { mutableStateOf(false) }
    var selectedState by remember { mutableStateOf(states[0]) }

    val filteredServices = services.filter { it.state == selectedState }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Seva App") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Select State",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedState,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("State") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    states.forEach { state ->
                        DropdownMenuItem(
                            text = { Text(state) },
                            onClick = {
                                selectedState = state
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Government Services",
                style = MaterialTheme.typography.headlineSmall
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = filteredServices) { service ->
                    ServiceCard(service = service) {
                        val intent = Intent(Intent.ACTION_VIEW, service.url.toUri())
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceCard(service: Service, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = service.name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Category: ${service.category}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "State: ${service.state}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
