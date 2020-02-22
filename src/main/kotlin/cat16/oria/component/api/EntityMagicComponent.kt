package cat16.oria.component.api

import cat16.oria.entity.player.ManaManager
import nerdhub.cardinal.components.api.component.extension.SyncedComponent
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent

interface EntityMagicComponent : EntitySyncedComponent {
    val manaManager: ManaManager
}