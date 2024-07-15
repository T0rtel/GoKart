package tortel.gokart.Commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import tortel.gokart.Vehicle.VehicleUtils

class RideCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, p2: String, arrays: Array<out String>?): Boolean {
        if (sender !is Player || arrays.isNullOrEmpty()) return false
        //START RIDING
        if (arrays[0] == "start"){ // player types /ride start
            VehicleUtils.spawnVehicle(sender) // then this happens
            return true
        }

        //STOP RIDING
        if (arrays[0]== "stop"){
            sender.velocity = Vector(0.0,100.0,0.0)
            return true
        }

        return false
    }
}