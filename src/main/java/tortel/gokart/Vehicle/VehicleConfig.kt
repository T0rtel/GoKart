package tortel.gokart.Vehicle

import org.bukkit.OfflinePlayer
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import tortel.gokart.Main
import java.io.File

object VehicleConfig {
    private var file: File = File("")
    private var config: YamlConfiguration = YamlConfiguration()
    private var configname = "vehiclesdata"

    fun load(){
        file = File(Main.dataFolderDir.path, "$configname.yml")

        if (!file.exists()){
            Main.instance?.saveResource("$configname.yml", false)
            Main.instance?.logger?.warning("$configname NOT FOUND")
        }

        config = YamlConfiguration()
        //config.options().parseComments(true)

        try{
            config.save(file)
            config.load(file)
            config.set("works", true)
            Main.instance?.logger?.info("$configname Config Setup Status : ${config.get("works")}")
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    fun reload(){
        file = File(Main.dataFolderDir.path, "$configname.yml")

        if (!file.exists()){
            Main.instance?.saveResource("$configname.yml", false)
            Main.instance?.logger?.warning("$configname NOT FOUND")
        }

        config = YamlConfiguration()

        try{
            config.load(file)
            config.set("works", true)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    fun save(){
        try{
            config.save(file)
        }catch (e: Exception){
            e.printStackTrace()
        }
        println(config.getList("vehicles"))
    }
    fun getConfig(): YamlConfiguration {
        return config
    }
    fun set(key: String, value: Any?){
        config.set(key, value)
        //save()
    }
    fun get(key : String): Any? {
        return config.get(key)
    }

}