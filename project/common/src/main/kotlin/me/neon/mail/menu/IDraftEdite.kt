package me.neon.mail.menu

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * NeonMail-Premium
 * me.neon.mail.common.menu.edit
 *
 * @author 老廖
 * @since 2024/1/9 1:04
 */
interface IDraftEdite {

    val player: Player

    val admin: Boolean

    fun openMenu() {
        player.openInventory(getInventory())
    }

    fun getInventory(): Inventory
}