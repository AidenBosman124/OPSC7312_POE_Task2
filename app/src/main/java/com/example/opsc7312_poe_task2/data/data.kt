data class RecentBirdObservation(
    val speciesCode: String,
    val comName: String,
    val locName: String,
    val obsDt: String
)
data class HotspotResponse(
    val hotspots: List<Hotspot>
)

data class Hotspot(
    val name: String,
    val latitude: Double,
    val longitude: Double
)
data class RecentBirdObservationsResponse(
    val recent: List<RecentBirdObservation>
)
