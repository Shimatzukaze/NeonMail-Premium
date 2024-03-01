package me.neon.mail.service.channel

import com.google.common.io.ByteStreams
import me.neon.mail.NeonMailLoader
import me.neon.mail.service.packet.AbstractPacket
import me.neon.mail.service.packet.IPacket
import org.bukkit.Bukkit
import taboolib.common.platform.function.info
import taboolib.platform.util.bukkitPlugin
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.logging.Level

/**
 * NeonMail-Premium
 * me.neon.mail.service.channel
 *
 * @author 老廖
 * @since 2024/1/18 3:27
 */
class PluginChannel: ChannelInit() {
    override fun sendPacket(packet: IPacket) {
        val out = ByteStreams.newDataOutput()
        out.writeUTF("Forward")
        out.writeUTF("ONLINE")
        out.writeUTF(serviceChannel)
        val bytes = ByteArrayOutputStream()
        val gout = DataOutputStream(bytes)
        gout.writeUTF((packet as AbstractPacket).getMessageData())
        val b = bytes.toByteArray()
        out.writeShort(b.size)
        out.write(b)
        Bukkit.getOnlinePlayers()
            .first()
            .sendPluginMessage(bukkitPlugin, bungeeCord, out.toByteArray())
    }

    override fun onStart() {
        info("使用插件消息通道进行通信...")
        Bukkit.getMessenger().registerOutgoingPluginChannel(bukkitPlugin, bungeeCord)
        Bukkit.getMessenger().registerIncomingPluginChannel(bukkitPlugin, bungeeCord) { channel, player, var3 ->
            if (channel == "BungeeCord") {
                val input = ByteStreams.newDataInput(var3)
                val subChannel = input.readUTF()
                if (subChannel.equals(serviceChannel)) {
                    val len: Short = input.readShort()
                    val bytes = ByteArray(len.toInt())
                    input.readFully(bytes)
                    val gin = DataInputStream(ByteArrayInputStream(bytes))
                    readMessage(gin.readUTF())
                }
            }
        }
    }

    override fun onClose() {
    }
}