package app.model

data class UserRequirements(
    val robustness: Double,
    val quality: Double,
    val performance: Double,
    val resourceUsage: Double,
    val flexibility: Double
)