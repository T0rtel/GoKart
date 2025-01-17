package tortel.gokart.listeners

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import org.bukkit.plugin.Plugin
import tortel.gokart.Vehicle.VehicleUtils


class VehicleInput(plugin : Plugin) : PacketAdapter(params().plugin(plugin).types(PacketType.Play.Client.STEER_VEHICLE)) {

    override fun onPacketReceiving(event: PacketEvent){
        //detect if player moves //STEER_VEHICLE
        if (event.packetType == PacketType.Play.Client.STEER_VEHICLE){
            val plr = event.player
            val packet = event.packet
            val frontAndBack = event.packet.float.read(1)
            val sides = event.packet.float.read(0)
            //println("Packet received: ${event.packet.float.read(0)} ${event.packet.float.read(1)}")
            if (frontAndBack == 0.98f){
                //println("W")
                VehicleUtils.moveForward(plr)
            }
            if (frontAndBack == -0.98f){
                println("S")
                VehicleUtils.moveBackward(plr)
            }
            if (sides == 0.98f){
                println("A")
                VehicleUtils.moveRight(plr)
            }
            if (sides == -0.98f){
                println("D")
                VehicleUtils.moveLeft(plr)
            }

        }

    }

}