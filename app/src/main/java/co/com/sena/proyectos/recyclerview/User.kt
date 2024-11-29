package co.com.sena.proyectos.recyclerview

data class User(
    val id: Int,
    var email: String,
    var first_name: String,
    var last_name: String,
    var avatar: String
){
    //Esto es fundamental para el manejo de información
    fun getFullName(): String = "$first_name $last_name"
}
