package tortel.gokart

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import tortel.gokart.commands.RideCommand
import tortel.gokart.listeners.VehicleInput
import tortel.gokart.tabCompleters.RideCommandTabCompleter
import tortel.gokart.vehicle.VehicleConfig
import tortel.gokart.vehicle.VehicleUtils
import java.io.File


class Main : JavaPlugin() {
    val pluginmanager = Bukkit.getPluginManager()
    private var protocolManager: ProtocolManager? = null

    companion object {
        var dataFolderDir: File = File("")
            private set
        var instance : Plugin? = null
            private set
    }//test

    override fun onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager!!.addPacketListener(VehicleInput(this))

        dataFolderDir = dataFolder
        instance = this

        loadConfigs()
        registerEvents()
        registerCommands()
        setupVehicleTickSystem()
        registerTabCompleters()
    }

    override fun onDisable() {
        unloadConfigs()
        removeAllItemDisplays()
    }

    private fun registerCommands(){
        getCommand("ride")?.setExecutor(RideCommand())
    }

    private fun registerTabCompleters(){
        getCommand("ride")?.tabCompleter = RideCommandTabCompleter()
    }

    private fun registerEvents(){
        //pluginmanager.registerEvents(PlayerMoveEvent(), this)
    }

    private fun loadConfigs(){
        VehicleConfig.load()
    }
    private fun unloadConfigs(){
        VehicleConfig.save()
    }

    private fun removeAllItemDisplays(){
        Bukkit.getWorlds()[0].entities.forEach {
            if (it.type == EntityType.ARMOR_STAND){
                it.remove()
            }//e
        }
    }

    private fun setupVehicleTickSystem(){
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
                        VehicleUtils.vehicleVelocities[it.name] = Vector() // this should fix it ig(hopefully)
//e
                    }

                    VehicleUtils.playersAccelerating[it] = false
                }
            }
        }.runTaskTimer(this, 1, 1)

    }

}
