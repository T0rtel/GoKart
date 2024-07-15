package tortel.gokart.Vehicle

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.*

object Vehicle {
    data class vehicle(var data: MutableMap<Any, Any>)
    private var licensePlate: String? = null
    private var owner: UUID? = null

    private var name: String? = null
    //private var isGlowing = false
    private var accelerationSpeed= 0.0
    private var maxSpeed = 0.0
    private var brakingSpeed = 0.0
    private var frictionSpeed = 0.0
    private var rotationSpeed= 0
    private var maxSpeedBackwards : Double? = 0.0
    private var newVehicle: ArmorStand? = null

    //private var vehicleData: Map<*, *>? = null

    // the goKart project is kotlin, gokartjava is in java
    //accelerationSpeed : Double,
    //maxSpeed : Double,
    //brakingSpeed : Double,frictionSpeed : Double, rotationSpeed : Int
    fun CreateVehicle(
        plr : Player,
        owner: UUID?,
        EngineForce : Double,
        DragConstant : Double,
        RollingResistanceConstant : Double,
        BrakingConstant : Double){
        //then create the armor stand
        val ownerPosition = plr.location // the ? is for if the Bukkit.getPlayer(owner) is nill(not there)
        val newVehicle = plr.world.spawnEntity(ownerPosition.add(Vector(0.0,0.0,0.0)), EntityType.ARMOR_STAND) as ArmorStand

        newVehicle.isInvulnerable = true
        newVehicle.isInvisible = true
        //newVehicle.setGravity(false)
        newVehicle.addPassenger(plr)
        newVehicle.isCustomNameVisible = false
        newVehicle.customName(Component.text("$owner"))
        //in here we just set the vehicle data n stuff this looks scuffed ik
        val list : List<Any> = listOf(
            "$owner",
            EngineForce,
            DragConstant,
            RollingResistanceConstant,
            BrakingConstant,
            //accelerationSpeed, // acceleration per tick
           // maxSpeed, // max speed
           // brakingSpeed, // braking speed per tick
           // frictionSpeed, // braking speed per tick
           // rotationSpeed, // braking speed per tick

            newVehicle
        )

        createVehicleData(plr, list)
    }

    fun createVehicleData(plr : Player, list : List<Any>) { //vehicle: MutableMap<Any, Any>
        VehicleUtils.vehiclesData[plr.name] = list
        VehicleUtils.vehiclesSpeeds[plr.name] = Vector(1.0, 0.0, 1.0) // initialize speed
        println("${VehicleUtils.vehiclesData}")
    }




    fun getLicensePlate(): String {
        return licensePlate!!
    }

    fun setLicensePlate(licensePlate: String?) {
        this.licensePlate = licensePlate
    }

    fun getName(): String {
        return name!!
    }


    fun getAccelerationSpeed(): Double {
        return accelerationSpeed
    }

    fun getMaxSpeed(): Double {
        return maxSpeed
    }

    fun getBrakingSpeed(): Double {
        return brakingSpeed
    }

    fun getFrictionSpeed(): Double {
        return frictionSpeed
    }

    fun getRotateSpeed(): Int {
        return rotationSpeed
    }


    fun getOwnerUUID(): UUID {
        return owner!!
    }

    fun getOwnerName(): String? {
        return Bukkit.getOfflinePlayer(getOwnerUUID()).name
    }

    fun isOwner(player: OfflinePlayer): Boolean {
        return owner == player.uniqueId
    }

    fun setName(name: String?) {
        this.name = name
    }


    fun setAccelerationSpeed(accelerationSpeed: Double) {
        this.accelerationSpeed = accelerationSpeed
    }

    fun setMaxSpeed(maxSpeed: Double) {
        this.maxSpeed = maxSpeed
    }

    fun setBrakingSpeed(brakingSpeed: Double) {
        this.brakingSpeed = brakingSpeed
    }

    fun setFrictionSpeed(frictionSpeed: Double) {
        this.frictionSpeed = frictionSpeed
    }

    fun setRotateSpeed(rotateSpeed: Int) {
        rotationSpeed = rotateSpeed
    }

    fun setOwner(owner: UUID?) {
        this.owner = owner
    }
    /*
    fun getVehicleData(): Map<*, *> {
        return vehicleData!!
    }

     */



    /**
     * Save vehicle's seats from vehicles.yml to VehicleData
     */


}