data class RecentBirdObservation(
    val speciesCode: String,
    val comName: String,
    val locName: String,
    val obsDt: String
)

data class RecentBirdObservationsResponse(
    val recent: List<RecentBirdObservation>
)
