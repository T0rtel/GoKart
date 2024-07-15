package tortel.gokart.tabCompleters

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class RideCommandTabCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>?
    ): List<String>? {
        if (sender !is Player) {
            return null
        }

        // Provide tab completion only for the "ride" command
        if (!cmd.name.equals("ride", ignoreCase = true)) {
            return null
        }

        // Provide suggestions based on the arguments
        if (args?.size == 1) {
            return listOf("start", "stop").filter { it.startsWith(args[0], ignoreCase = true) }
        }
        return emptyList()
    }
}