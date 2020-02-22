package cat16.oria.util

import cat16.oria.Oria

interface OriaObjectInfo {
    val oriaName: String
    val typeName: String
    fun key(name: String? = null) = Oria.key(typeName, this.oriaName) + if(name != null) ".$name" else ""
}
