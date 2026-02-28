package com.seva.app

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.seva.app.data.ServiceRepo
import com.seva.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    initialCategory: String? = null,
    initialState: String? = "Select a state",
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var selectedState by remember { mutableStateOf(initialState) }
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    var stateExpanded by remember { mutableStateOf(false) }

    val filteredServices = remember(selectedState, selectedCategory) {
        ServiceRepo.getFilteredServices(selectedState, selectedCategory)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SevaLightBg)
    ) {
        TopAppBar(
            title = { 
                Text(
                    text = if (selectedCategory != null) selectedCategory!! else "All Services",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SevaDarkBlue
                ) 
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = SevaDarkBlue)
                }
            },
            actions = {
                IconButton(onClick = { /* Handle Filter */ }) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter", tint = SevaDarkBlue)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            // State Filter Pill/Dropdown in Services Screen
            Text(
                text = "Viewing services for",
                style = MaterialTheme.typography.labelMedium,
                color = SevaGrey
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Box {
                Surface(
                    onClick = { stateExpanded = true },
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = selectedState ?: "Select State",
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (selectedState == "Select a state") SevaGrey else SevaDarkBlue
                        )
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = null,
                            tint = SevaOrange,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                DropdownMenu(
                    expanded = stateExpanded,
                    onDismissRequest = { stateExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    ServiceRepo.states.forEach { state ->
                        DropdownMenuItem(
                            text = { Text(state) },
                            onClick = {
                                selectedState = state
                                stateExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (filteredServices.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No services found for this selection.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = SevaGrey
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(filteredServices) { service ->
                        ServiceItemCard(service = service) {
                            val intent = Intent(Intent.ACTION_VIEW, service.url.toUri())
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceItemCard(service: com.seva.app.data.Service, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = service.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = SevaDarkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = service.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = SevaOrange,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Surface(
                    color = SevaBadgeBg,
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    Text(
                        text = service.state,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = SevaBadgeText
                    )
                }
            }
            
            if (service.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = service.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = SevaGrey,
                    lineHeight = 18.sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Visit Official Website →",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = SevaOrange
            )
        }
    }
}
