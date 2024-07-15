package tortel.gokart.Vehicle

public object VehicleData {

    var speed = HashMap<String, Double>()
    var maxSpeed = HashMap<String, Double>()
    var vehicleLocation = HashMap<String, Double>()
    var rotationSpeed = HashMap<String, Int>()
    var accelerationSpeed = HashMap<String, Double>()
    var brakingSpeed = HashMap<String, Double>()
    var frictionSpeed = HashMap<String, Double>()
    var lastRegions = HashMap<String, Set<String>>()
    var destroyedVehicles = HashMap<String, Boolean>()

    var Vehicles = HashMap<String, Boolean>()
}