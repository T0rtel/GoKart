package tortel.gokart

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import tortel.gokart.Commands.RideCommand
import tortel.gokart.Listeners.VehicleInput
import tortel.gokart.Vehicle.VehicleConfig
import tortel.gokart.Vehicle.VehicleUtils
import java.io.File


class Main : JavaPlugin() {
    val pluginmanager = Bukkit.getPluginManager()
    private var protocolManager: ProtocolManager? = null

    companion object {
        var dataFolderDir: File = File("")
            private set
        var instance : Plugin? = null
            private set
    }

    override fun onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager!!.addPacketListener(VehicleInput(this))

        dataFolderDir = dataFolder
        instance = this

        loadConfigs() // functions to load everything when plugin starts
        registerEvents()
        registerCommands()
        setupVehicleTickSystem()
    }

    override fun onDisable() {
        unloadConfigs() // unload ondisable
        removeAllVehicles()
    }

    fun registerCommands(){
        getCommand("ride")?.setExecutor(RideCommand())
        // register the command i added in plugin.yml under resoures(where you add commands and their properties) then we set the
        // executor which is the class we want to fire when we use the command ingame
    }

    fun registerEvents(){
        //pluginmanager.registerEvents(PlayerMoveEvent(), this)
    }

    fun loadConfigs(){
        VehicleConfig.load() // ignore the vehicleconfig for now we arent even using it
    }
    fun unloadConfigs(){
        VehicleConfig.save()
    }

    fun removeAllVehicles(){ // remove all vehicles
        Bukkit.getWorlds()[0].entities.forEach {
            val entity = it
            if (entity.type == EntityType.ARMOR_STAND){
                entity.remove()
            }
        }
    }

    // this "tick system" was used in decreasing the speed every second when player isnt having inputs to drive.
    fun setupVehicleTickSystem(){
        object : BukkitRunnable() {
            override fun run() {

                Bukkit.getOnlinePlayers().forEach { // for every online player, if they are not accelerating(no inputs) then decrease their speed
                    //but i commented everything
                    //println(VehicleUtils.vehiclesSpeeds[it.name])
                    if (VehicleUtils.playersAccelerating[it] == false){
                        if (VehicleUtils.vehiclesData[it.name] == null) return
                        val vehicleData = VehicleUtils.vehiclesData[it.name]!!
                        //val vehicleAccelerationSpeed : Double = vehicleData.get(2) as Double
                        /*
                        if (VehicleUtils.vehiclesSpeeds[it.name]!! > 0){
                            VehicleUtils.vehiclesSpeeds[it.name] = VehicleUtils.vehiclesSpeeds[it.name]!! - vehicleAccelerationSpeed
                        }

                         */
                        VehicleUtils.vehicleVelocities[it.name] = Vector() // this should fix it ig(hopefully)

                    }

                    VehicleUtils.playersAccelerating[it] = false
                }
            }
        }.runTaskTimer(this, 1, 1)

    }

}
