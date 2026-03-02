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
        "Emergency Services",
        "Environmental and pollution complaints",
        "Public Safety and Civic Issues",
        "Women and child Safety",
        "Health and Sanitation"
    )

    val states = listOf(
        "Maharashtra"
    )

    val services = listOf(
        // Emergency Services
        Service("Fire Brigade", "Emergency Services", "Maharashtra", "https://mahafireservice.gov.in/", "24/7 Fire and rescue services."),
        Service("Police", "Emergency Services", "Maharashtra", "https://mumbaipolice.gov.in/", "Direct contact with Maharashtra Police."),
        Service("Ambulance", "Emergency Services", "Maharashtra", "https://108.gov.in/", "Emergency medical transport services."),
        Service("Disaster Management", "Emergency Services", "Maharashtra", "https://mdm.maharashtra.gov.in/", "Disaster response and relief coordination."),
        
        // Environmental and pollution complaints
        Service("Noise Pollution", "Environmental and pollution complaints", "Maharashtra", "https://mpcb.gov.in/", "Report excessive noise levels."),
        Service("Air Pollution", "Environmental and pollution complaints", "Maharashtra", "https://mpcb.gov.in/", "Monitor and report air quality issues."),
        Service("Water Pollution", "Environmental and pollution complaints", "Maharashtra", "https://mpcb.gov.in/", "Report industrial or urban water waste."),
        Service("Smoke/Burning", "Environmental and pollution complaints", "Maharashtra", "https://mpcb.gov.in/", "Report illegal waste or crop burning."),

        // Public Safety and Civic Issues
        Service("Road Safety", "Public Safety and Civic Issues", "Maharashtra", "https://transport.maharashtra.gov.in/", "Safety guidelines and road condition reports."),
        Service("Traffic Violations", "Public Safety and Civic Issues", "Maharashtra", "https://mahatrafficticket.gov.in/", "Check and pay traffic fines."),
        Service("Streetlight/Infrastructure", "Public Safety and Civic Issues", "Maharashtra", "https://mcmc.gov.in/", "Report broken streetlights or potholes."),
        Service("Waste Management", "Public Safety and Civic Issues", "Maharashtra", "https://swachhbharatmission.gov.in/", "Report garbage collection issues."),

        // Women and child Safety
        Service("Women Helpline", "Women and child Safety", "Maharashtra", "https://wcd.maharashtra.gov.in/", "181 Women helpline services."),
        Service("Child Support", "Women and child Safety", "Maharashtra", "https://1098.gov.in/", "1098 Childline emergency response."),
        Service("Domestic Violence Support", "Women and child Safety", "Maharashtra", "https://mumbaipolice.gov.in/WomenSafety", "Legal help and safe shelter reporting."),
        Service("Cyber Crime Support", "Women and child Safety", "Maharashtra", "https://www.cybercrime.gov.in/", "Report online harassment and safety issues."),

        // Health and Sanitation
        Service("Public Health", "Health and Sanitation", "Maharashtra", "https://arogya.maharashtra.gov.in/", "General health complaints and info."),
        Service("Food Safety", "Health and Sanitation", "Maharashtra", "https://fda.maharashtra.gov.in/", "Report expired or adulterated food."),
        Service("Sanitation/Hygiene", "Health and Sanitation", "Maharashtra", "https://swachhbharatmission.gov.in/", "Public toilet and hygiene complaints."),
        Service("Medical Facilities", "Health and Sanitation", "Maharashtra", "https://dmer.maharashtra.gov.in/", "Locate government hospitals and clinics.")
    )

    fun getFilteredServices(state: String?, category: String?): List<Service> {
        return services.filter { service ->
            (state == null || state == "Select a state" || service.state == state) &&
            (category == null || service.category == category)
        }
    }
}
