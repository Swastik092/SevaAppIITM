package com.seva.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import com.seva.app.ui.theme.*
import com.seva.app.data.ServiceRepo

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    selectedState: String,
    onStateChange: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onViewAllClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Simple Top Bar for Home
        CenterAlignedTopAppBar(
            title = { 
                Text(
                    "Seva App", 
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SevaDarkBlue
                ) 
            },
            navigationIcon = {
                // Left side blank as requested
            },
            actions = {
                IconButton(onClick = { /* Handle menu */ }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = SevaDarkBlue)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
        ) {
            item {
                // Search Bar Section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(56.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color(0xFFF1F3F4))
                        .clickable { /* Handle search focus */ }
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = SevaGrey,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Search for the service",
                            color = SevaGrey,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Banner Section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    // Background Image (India Gate)
                    Image(
                        painter = painterResource(id = R.drawable.india_gate),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(4.dp),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Gradient Overlay for Readability
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        SevaDarkBlue.copy(alpha = 0.7f)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Welcome to Seva",
                            color = Color.White.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Empowering Citizens",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            item {
                // State Selection Section
                Text(
                    text = "Select Your State",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = SevaDarkBlue
                )
                Text(
                    text = "Choose your location to see available government services.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SevaGrey,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Added "State" label matching image
                Text(
                    text = "State",
                    style = MaterialTheme.typography.labelSmall,
                    color = SevaOrange,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Custom Dropdown Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF8F9FA)) // Slightly off-white matching image
                        .border(1.dp, SevaGrey.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                        .clickable { expanded = true }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = selectedState,
                            color = if (selectedState == "Select a state") SevaGrey else SevaDarkBlue,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = SevaOrange
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {
                        ServiceRepo.states.forEach { state ->
                            DropdownMenuItem(
                                text = { Text(state) },
                                onClick = {
                                    onStateChange(state)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Emergency Services Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Emergency Services",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = SevaDarkBlue
                    )
                    Surface(
                        color = Color(0xFFFFE5E5),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "24/7 AVAILABLE",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFE53935),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Emergency Quick Links Grid (2x2)
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EmergencySmallCard(Modifier.weight(1f), "Fire Brigade", "101", Color(0xFFFFF3E0), Color(0xFFFB8C00), Icons.Default.FireTruck)
                        EmergencySmallCard(Modifier.weight(1f), "Police", "100", Color(0xFFE3F2FD), Color(0xFF1E88E5), Icons.Default.Security)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EmergencySmallCard(Modifier.weight(1f), "Ambulance", "102", Color(0xFFE8F5E9), Color(0xFF43A047), Icons.Default.MedicalServices)
                        EmergencySmallCard(Modifier.weight(1f), "Disaster", "108", Color(0xFFFFEBEE), Color(0xFFD32F2F), Icons.Default.Warning)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Environmental and Pollution Complaints Section
            item {
                Text(
                    text = "Environmental and pollution complaints",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SevaDarkBlue
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Environmental Quick Links Grid (2x2)
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EmergencySmallCard(Modifier.weight(1f), "Noise Pollution", "Complaint", Color(0xFFF3E5F5), Color(0xFF8E24AA), Icons.Default.VolumeUp)
                        EmergencySmallCard(Modifier.weight(1f), "Air Pollution", "Complaint", Color(0xFFE0F7FA), Color(0xFF00838F), Icons.Default.Air)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EmergencySmallCard(Modifier.weight(1f), "Water Pollution", "Complaint", Color(0xFFE1F5FE), Color(0xFF0277BD), Icons.Default.WaterDrop)
                        EmergencySmallCard(Modifier.weight(1f), "Smoke/Burning", "Complaint", Color(0xFFFBE9E7), Color(0xFFBF360C), Icons.Default.SmokingRooms)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Public Safety and Civic Issues Section
            item {
                Text(
                    text = "Public safety and Civic issues",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SevaDarkBlue
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Public Safety Quick Links Grid (2x2)
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EmergencySmallCard(Modifier.weight(1f), "Road Safety", "Issue", Color(0xFFFFF8E1), Color(0xFFFFA000), Icons.Default.AddRoad)
                        EmergencySmallCard(Modifier.weight(1f), "Traffic", "Issue", Color(0xFFFFEBEE), Color(0xFFE53935), Icons.Default.Traffic)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EmergencySmallCard(Modifier.weight(1f), "Streetlight", "Issue", Color(0xFFE1F5FE), Color(0xFF039BE5), Icons.Default.Lightbulb)
                        EmergencySmallCard(Modifier.weight(1f), "Waste", "Issue", Color(0xFFE8F5E9), Color(0xFF43A047), Icons.Default.Delete)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Women and Child Safety Section
            item {
                Text(
                    text = "Women and child Safety",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SevaDarkBlue
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Women and Child Safety Quick Links Grid (2x2)
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EmergencySmallCard(Modifier.weight(1f), "Women Helpline", "Helpline", Color(0xFFFCE4EC), Color(0xFFD81B60), Icons.Default.Woman)
                        EmergencySmallCard(Modifier.weight(1f), "Child Support", "Helpline", Color(0xFFF3E5F5), Color(0xFF7B1FA2), Icons.Default.ChildCare)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EmergencySmallCard(Modifier.weight(1f), "Domestic Violence", "Support", Color(0xFFEFEBE9), Color(0xFF5D4037), Icons.Default.GppMaybe)
                        EmergencySmallCard(Modifier.weight(1f), "Cyber Crime", "Support", Color(0xFFECEFF1), Color(0xFF455A64), Icons.Default.Computer)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun EmergencySmallCard(
    modifier: Modifier = Modifier,
    title: String,
    number: String,
    bgColor: Color,
    tint: Color,
    icon: ImageVector
) {
    Surface(
        modifier = modifier.height(80.dp),
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.labelLarge, color = SevaDarkBlue, fontWeight = FontWeight.Bold)
                Text(text = number, style = MaterialTheme.typography.bodySmall, color = tint, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: ImageVector,
    iconColor: Color,
    tint: Color,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = SevaDarkBlue
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = SevaGrey,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = SevaGrey.copy(alpha = 0.5f)
            )
        }
    }
}
@Composable
fun CategoriesScreen(onCategoryClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SevaLightBg)
    ) {
        // Top section with title
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)) {
            Text(
                text = "Service Categories",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = SevaDarkBlue
            )
            Text(
                text = "Browse services by category to find what you need quickly.",
                style = MaterialTheme.typography.bodyMedium,
                color = SevaGrey,
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                CategoryCard(
                    title = "Emergency Services",
                    description = "Police, Fire, Ambulance and Disaster relief.",
                    icon = Icons.Default.Emergency,
                    iconColor = Color(0xFFFFE5E5),
                    tint = Color(0xFFE53935),
                    onClick = { onCategoryClick("Emergency Services") }
                )
            }
            item {
                CategoryCard(
                    title = "Environmental and pollution complaints",
                    description = "Noise, Air, Water pollution and waste burning.",
                    icon = Icons.Default.Eco,
                    iconColor = Color(0xFFE8F5E9),
                    tint = Color(0xFF43A047),
                    onClick = { onCategoryClick("Environmental") }
                )
            }
            item {
                CategoryCard(
                    title = "Public Safety and Civic Issues",
                    description = "Road safety, traffic, streetlights and waste.",
                    icon = Icons.Default.Security,
                    iconColor = Color(0xFFE3F2FD),
                    tint = Color(0xFF1E88E5),
                    onClick = { onCategoryClick("Public Safety") }
                )
            }
            item {
                CategoryCard(
                    title = "Women and child Safety",
                    description = "Helplines, domestic violence and child support.",
                    icon = Icons.Default.Woman,
                    iconColor = Color(0xFFFCE4EC),
                    tint = Color(0xFFD81B60),
                    onClick = { onCategoryClick("Women Safety") }
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
