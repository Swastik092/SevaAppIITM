package com.seva.app.data

data class Service(
    val name: String,
    val category: String,
    val state: String,
    val url: String,
    val description: String = ""
)

object ServiceRepo {
    val categories = listOf(
        "Emergency",
        "Women Safety",
        "Public Safety",
        "Documents",
        "Environmental",
        "Traffic"
    )

    val states = listOf(
        "Maharashtra"
    )

    val services = listOf(
        // Maharashtra
        Service("Maharashtra Emergency", "Emergency", "Maharashtra", "https://www.maharashtra.gov.in/", "24/7 Emergency response services."),
        Service("Maharashtra Women Safety", "Women Safety", "Maharashtra", "https://mumbaipolice.gov.in/WomenSafety", "Safety resources for women in Maharashtra."),
        Service("MAHA-IT Portal", "Documents", "Maharashtra", "https://www.mahaonline.gov.in/", "Apply for certificates and documents online."),
        
        // Karnataka
        Service("Karnataka Emergency", "Emergency", "Karnataka", "https://www.karnataka.gov.in/", "State emergency contact portal."),
        Service("Karnataka Women Safety", "Women Safety", "Karnataka", "https://ksp.karnataka.gov.in/page/Women+Safety/en", "Dedicated safety portal for women."),
        Service("Sakala Services", "Documents", "Karnataka", "https://www.sakala.kar.nic.in/", "Time-bound delivery of government services."),
        
        // Delhi
        Service("Delhi Emergency", "Emergency", "Delhi", "https://delhi.gov.in/", "Central emergency services for the capital."),
        Service("Delhi Women Safety", "Women Safety", "Delhi", "https://www.delhipolice.nic.in/", "Delhi Police women safety initiatives."),
        Service("e-District Delhi", "Documents", "Delhi", "https://edistrict.delhigovt.nic.in/", "Online gateway for Delhi government services."),
        
        // Generic/Placeholder for others (as placeholders for production-grade scale)
        Service("Gujarat State Portal", "Public Safety", "Gujarat", "https://gujaratindia.gov.in/", "Official Gujarat government portal."),
        Service("TN e-Sevai", "Documents", "Tamil Nadu", "https://www.tnesevai.tn.gov.in/", "Tamil Nadu e-Governance services."),
        Service("UP e-District", "Documents", "Uttar Pradesh", "https://edistrict.up.gov.in/", "Uttar Pradesh digital services portal."),
        Service("WB State Portal", "Public Safety", "West Bengal", "https://www.wb.gov.in/", "Official West Bengal government portal.")
    )

    fun getFilteredServices(state: String?, category: String?): List<Service> {
        return services.filter { service ->
            (state == null || state == "Select a state" || service.state == state) &&
            (category == null || service.category == category)
        }
    }
}
