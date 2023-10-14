package com.example.opsc7312_poe_task2

// This class represents the top-level object that holds a list of routes.
class GoogleMapDTO {
    var routes = ArrayList<Routes>()
}

// This class represents a single route.
class Routes {
    var legs = ArrayList<Legs>()
}

// This class represents a leg within a route.
class Legs {
    var distance = Distance()
    var duration = Duration()
    var end_address = ""
    var start_address = ""
    var end_location = Location()
    var start_location = Location()
    var steps = ArrayList<Steps>()
}

// This class represents a step within a leg.
class Steps {
    var distance = Distance()
    var duration = Duration()
    var end_address = ""
    var start_address = ""
    var end_location = Location()
    var start_location = Location()
    var polyline = PolyLine()
    var travel_mode = ""
    var maneuver = ""
}

// This class represents duration information.
class Duration {
    var text = ""
    var value = 0
}

// This class represents distance information.
class Distance {
    var text = ""
    var value = 0
}

// This class represents polyline information.
class PolyLine {
    var points = ""
}

// This class represents location information with latitude and longitude.
class Location {
    var lat = ""
    var lng = ""
}
