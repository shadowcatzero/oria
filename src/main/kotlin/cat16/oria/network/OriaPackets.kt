package cat16.oria.network

import cat16.oria.Oria.id
import net.minecraft.util.Identifier

object OriaPackets {

    @JvmField
    val BREAK_SOUL_ORB_PACKET_ID = packet("soul_orb_break")
    @JvmField
    val REMOVE_CURSOR_STACK_PACKET_ID = packet("cursor_stack_remove")

    fun init() { /* init fields */ }

    private fun packet(name: String): Identifier {
        return id(name)
    }
}