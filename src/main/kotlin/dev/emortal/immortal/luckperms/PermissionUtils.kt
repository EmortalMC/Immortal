package dev.emortal.immortal.luckperms

import dev.emortal.immortal.ImmortalExtension
import net.luckperms.api.model.user.User
import net.minestom.server.command.CommandSender
import net.minestom.server.command.ConsoleSender
import net.minestom.server.entity.Player
import world.cepi.kstom.adventure.asMini

object PermissionUtils {
    val userToPlayerMap = mutableMapOf<User, Player>()
    val playerAdapter = ImmortalExtension.luckperms.getPlayerAdapter(Player::class.java)

    val Player.lpUser: User get() {
        val user = playerAdapter.getUser(this)
        userToPlayerMap.putIfAbsent(user, this)
        return user
    }

    val Player.prefix: String? get() = lpUser.cachedData.metaData.prefix
    val Player.suffix: String? get() = lpUser.cachedData.metaData.suffix

    fun CommandSender.hasLuckPermission(permission: String): Boolean {
        if (this is ConsoleSender) return true
        if (this is Player) {
            return lpUser.cachedData.permissionData.checkPermission(permission).asBoolean()
        }
        return false
    }

    internal fun refreshPrefix(player: Player) {
        val prefix = player.prefix ?: return
        player.displayName = "$prefix ${player.username}".asMini()
    }
}