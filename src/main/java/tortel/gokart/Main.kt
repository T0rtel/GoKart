package tortel.gokart

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
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

        loadConfigs() //loads configs
        registerEvents()
        registerCommands()
        setupVehicleTickSystem()
    }

    override fun onDisable() {
        unloadConfigs()
        removeAllItemDisplays()
    }

    fun registerCommands(){
        getCommand("ride")?.setExecutor(RideCommand())
    }

    fun registerEvents(){
        //pluginmanager.registerEvents(PlayerMoveEvent(), this)
    }

    fun loadConfigs(){
        VehicleConfig.load()
    }
    fun unloadConfigs(){
        VehicleConfig.save()
    }

    fun removeAllItemDisplays(){
        Bukkit.getWorlds()[0].entities.forEach {
            if (it.type == EntityType.ARMOR_STAND){
                it.remove()
            }
        }
    }

    fun setupVehicleTickSystem(){
        object : BukkitRunnable() {
            override fun run() {

                Bukkit.getOnlinePlayers().forEach {
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
                        VehicleUtils.vehiclesSpeeds[it.name] = Vector() // this should fix it ig(hopefully)

                    }

                    VehicleUtils.playersAccelerating[it] = false
                }
            }
        }.runTaskTimer(this, 1, 1)

    }

}
