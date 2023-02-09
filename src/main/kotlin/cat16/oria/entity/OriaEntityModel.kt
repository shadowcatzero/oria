package cat16.oria.entity

import cat16.oria.Oria
import software.bernie.geckolib.core.animatable.GeoAnimatable
import software.bernie.geckolib.model.DefaultedEntityGeoModel

abstract class OriaEntityModel<T>(info: OriaEntityInfo) :
    DefaultedEntityGeoModel<T>(Oria.id(info.oriaName)) where T : GeoAnimatable