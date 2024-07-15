package tortel.gokart.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import tortel.gokart.vehicle.VehicleUtils

class RideCommand : CommandExecutor {

    data class Result(val message: String, val color: NamedTextColor)

    override fun onCommand(sender: CommandSender, cmd: Command, p2: String, arrays: Array<out String>?): Boolean {

        if (sender !is Player) {
            sender.sendMessage("Only players can use this command.")
            return true
        }

        if (!p2.equals("ride", ignoreCase = true)) {
            return true
        }

        if (arrays == null || arrays.isEmpty()) return true

        if (arrays[0].isEmpty()) {
            sender.sendMessage(Component.text("Usage: /ride <start | stop>").color(NamedTextColor.YELLOW))
            return true
        }

        val result = when (arrays[0].lowercase()) {
            "start" -> handleStart(sender) // Call handleStart for "start" argument
            "stop" -> handleStop(sender) // Call handleStop for "stop" argument
            else -> handleDefault() // Handle invalid arguments
        }

        sender.sendMessage(Component.text(result.message).color(result.color))

        return true

    }

    // Method to handle the "start" command
    private fun handleStart(player: Player): Result {
        // Add your custom start logic here
        VehicleUtils.spawnVehicle(player) // then this happens

        player.sendMessage(Component.text("Start logic executed").color(NamedTextColor.GREEN))
        return Result("Riding started!", NamedTextColor.YELLOW)
    }

    // Method to handle the "stop" command
    private fun handleStop(player: Player): Result {
        // Add your custom stop logic here
        player.velocity = Vector(0.0,100.0,0.0)


        player.sendMessage(Component.text("Stop logic executed").color(NamedTextColor.GREEN))
        return Result("Riding stopped!", NamedTextColor.RED)
    }

    // Method to handle invalid arguments
    private fun handleDefault(): Result {
        return Result("Riding failed! Invalid argument", NamedTextColor.RED)
    }

}