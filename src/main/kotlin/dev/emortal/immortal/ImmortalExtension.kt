package dev.emortal.immortal

import dev.emortal.immortal.blockhandler.CampfireHandler
import dev.emortal.immortal.blockhandler.SignHandler
import dev.emortal.immortal.blockhandler.SkullHandler
import dev.emortal.immortal.commands.PlayCommand
import dev.emortal.immortal.game.GameManager.game
import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.extensions.Extension
import net.minestom.server.utils.NamespaceID
import net.minestom.server.world.DimensionType
import world.cepi.kstom.Manager
import world.cepi.kstom.event.listenOnly
import world.cepi.kstom.util.register

class ImmortalExtension : Extension() {

    override fun initialize() {
        eventNode.listenOnly<PlayerDisconnectEvent> {
            player.game?.removePlayer(player)
        }
        eventNode.listenOnly<PlayerSpawnEvent> {
            if (player.game != null) {
                if (player.game!!.instance != spawnInstance) {
                    player.game!!.removePlayer(player)
                }
            }
        }

        val dimensionType = DimensionType.builder(NamespaceID.from("fullbright"))
            .ambientLight(1f)
            .build()
        Manager.dimensionType.addDimension(dimensionType)

        SignHandler.register("minecraft:sign")
        CampfireHandler.register("minecraft:campfire")
        SkullHandler.register("minecraft:skull")

        PlayCommand.register()

        logger.info("[Immortal] Initialized!")
    }

    override fun terminate() {
        PlayCommand.register()

        logger.info("[Immortal] Terminated!")
    }

}