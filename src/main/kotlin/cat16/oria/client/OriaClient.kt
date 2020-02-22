package cat16.oria.client

import cat16.oria.block.OriaBlocks
import cat16.oria.entity.OriaEntities
import cat16.oria.network.OriaPackets
import net.fabricmc.api.ClientModInitializer

class OriaClient : ClientModInitializer {
    override fun onInitializeClient() {
        OriaPackets.clientInit()
        OriaEntities.clientInit()
        OriaBlocks.clientInit()
    }
}