package tortel.gokart.Vehicle

import org.bukkit.ChatColor
import org.bukkit.entity.ArmorStand

import org.bukkit.entity.Player
import org.bukkit.util.Vector
import kotlin.math.sqrt
import kotlin.time.times

object VehicleUtils {
    var licenseplates : List<String> = listOf<String>()
    val vehiclesData : HashMap<String, List<Any>> = HashMap()
    val vehiclesSpeeds : HashMap<String, Vector> = HashMap()
    val playersAccelerating : HashMap<Player, Boolean> = HashMap()

    fun spawnVehicle(plr : Player){ // in here we initialize the vehicle properties and spawn the armor stand(for now)
        /*
        Vehicle.CreateVehicle(
            plr,
            plr.uniqueId,
            false,
            0.025, // per tick, *20 to see per second
            0.5, // 10 m/s
            1.0,
            1.0,
            1
        )

         */

        Vehicle.CreateVehicle(
            plr,
            plr.uniqueId,
            2.0,
            0.01,
            0.01,
            0.05,
        )

        return
    }

    fun despawnVehicle(license : String){

    }

    fun setVehicleVelocity(plr : Player){
        if (vehiclesData[plr.name] == null) return
        val vehicleData = vehiclesData[plr.name]!!
        val EngineForce= vehicleData[1] as Double
        val DragConstant = vehicleData[2] as Double
        val RollingResConstant = vehicleData[3] as Double
        val BrakingConstant = vehicleData[4] as Double
        val Vehicle : ArmorStand = vehicleData[5] as ArmorStand

        var TractionForce : Vector? = null
        var DragForce : Vector? = null
        var RollingResForce : Vector? = null
        var LongitudinalForce : Vector? = null
        var velocity : Vector = vehiclesSpeeds[plr.name]!!
        var speed = Math.sqrt(velocity.x *velocity.x + velocity.z*velocity.z)

        println("old velocity X: ${velocity.x}")
        println("old velocity Z: ${velocity.z}")
        println("speed: $speed")
        val Mass = 5

        val plrLoc = plr.location
        val plrDir = plrLoc.direction

        val DeltaTime = 3

        val VehiclePos = Vehicle.location
        //traction force is the force applied when player moves, its just engine force applied
        TractionForce = Vector(plrDir.x, 0.0, plrDir.z).multiply(Vector(EngineForce, 0.0, EngineForce))
        //drag force is the drag... just a negative value
        DragForce = Vector(-DragConstant * velocity.x * speed,0.0, -DragConstant * velocity.z * speed)
        //rolling resistance force is rolling resistance, which (is supposed to) be the friction of the tire and the ground
        RollingResForce = Vector(-RollingResConstant * velocity.x,0.0, -RollingResConstant * velocity.z)
        //the longitudinal force is the force that acts longitudinally on the kart. (moves in front or back)
        LongitudinalForce =
        Vector(TractionForce.x  + RollingResForce.x + DragForce.x , 0.0, TractionForce.z + RollingResForce.z + DragForce.z)

        val acceleration = Vector(LongitudinalForce.x / Mass, 0.0, LongitudinalForce.z / Mass)
        velocity = Vector(velocity.x + (DeltaTime * acceleration.x), 0.0, velocity.z +(DeltaTime * acceleration.z))

        vehiclesSpeeds[plr.name] = velocity // every tick we are saving the last velocity, and when the player stops holding W for ex, it resets it(that is in Main)

        //Vehicle.teleport(VehiclePos.add(velocity))
        Vehicle.velocity = velocity
        println("VEL: $velocity || ACC: $acceleration")
        println("TRACTION: $TractionForce")
        println("DRAG FORCE: $DragForce")
        println("RollingResistance: $RollingResForce")
        println("LongitudinalForce: $LongitudinalForce")


        /*
        playersAccelerating[plr] = true
        val vehicleData = vehiclesData.get(plr.name)
        val vehicleObject : ArmorStand = vehicleData?.last as ArmorStand
        val vehicleAccelerationSpeed : Double = vehicleData.get(2) as Double
        val vehicleMaxSpeed = vehicleData[3] as Double
        val loc = plr.location
        val oldspeed =  vehiclesSpeeds[plr.name]!!
        if (oldspeed < vehicleMaxSpeed){
            vehiclesSpeeds[plr.name] = (oldspeed + vehicleAccelerationSpeed)
            if (vehiclesSpeeds[plr.name]!! > vehicleMaxSpeed) { // so it doesn't go above the limit ->
                vehiclesSpeeds[plr.name] = vehicleMaxSpeed
            }
        }
        val speed = vehiclesSpeeds[plr.name] as Double
        vehicleObject.velocity = Vector(loc.direction.multiply(speed).x, -0.8, loc.direction.multiply(speed).z)

         */
    }

    fun moveForward(plr : Player){
        val vehicledata = vehiclesData.get(plr.name)
        setVehicleVelocity(plr)
    }

    fun moveBackward(plr : Player){
        val vehicledata = vehiclesData.get(plr.name)

    }

    fun moveRight(plr : Player){
        val vehicledata = vehiclesData.get(plr.name)

    }

    fun moveLeft(plr : Player){
        val vehicledata = vehiclesData.get(plr.name)

    }

    fun generatelicense(): String {
        return "TS-TS-TS"
    }

    fun getDrivenVehiclePlate(p: Player): String? {
        if (p.vehicle == null) return null
        if (!p.vehicle!!.customName!!.contains("MTVEHICLES_")) return null
        val name = p.vehicle!!.customName!!.split("_".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        return name[2]
    }

    fun vehicleExists(plr: Player): Boolean {
        val config = VehicleConfig.getConfig()

        if (config.get(plr.name) == null){
            return false
        }

        return true
    }

    fun getVehicleData(plr: Player): MutableList<MutableMap<*, *>> {
        val config = VehicleConfig.getConfig()
        return config.getMapList(plr.name)
    }

    fun colorizeText(text: String?): String {
        return ChatColor.translateAlternateColorCodes('&', text!!)
    }

}