package co.com.sena.proyectos.recyclerview
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.sena.proyectos.recyclerview.databinding.ActivityMainBinding
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import android.util.Log


class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager


    //Reutilizar un método constantemente, con una vista ya prediseñada
    //-> Los contáctos manejan este tipo de información
    //-> Una vista se crea sin parte lógica. Esto se crea mediante un adaptador que organiza los datos.
    //-:NECECITAMOS EL NOMBRE, LA IMÁGEN. Estructura, pasar la información en base a usuarios.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPrincipal.layoutManager = LinearLayoutManager(this)
        getUser(object : UserCallback {
            override fun onUsersLoaded(users: List<User>) {
                Log.d("USER_LIST", "Users loaded: $users")
                userAdapter = UserAdapter(users)
                binding.rvPrincipal.adapter = userAdapter
            }
        })
        //Colocar una splash a la aplicaicon
        //Colocar un ícono a la aplicacion

        //inicio configuracion Shared Preference
        val preference = getPreferences(Context.MODE_PRIVATE)
        val isFirstTime = preference.getBoolean(getString(R.string.sp_ft), true)
        Log.i("VARIABLE_SP", "EL VALOR DEL first_time = $isFirstTime")


        // fin configuraciones
        //userAdapter = UserAdapter(getUser())
        //linearLayoutManager = LinearLayoutManager(this)
        //binding.rvPrincipal.apply {
            //adapter = userAdapter
            //layoutManager = linearLayoutManager
        //}
    }

    private fun getUser(callback: UserCallback) {
        val users = mutableListOf<User>()
        val url = "https://reqres.in/api/users"
        val queue = Volley.newRequestQueue(this)
        val jsonObjReq = JsonObjectRequest(
            Request.Method.GET, url, null,
            { res -> // Éxito
                Log.d("API_RESPONSE", "Response: $res") // Agrega esto para ver la respuesta en el Logcat
                val arrayApi = res.getJSONArray("data")
                for (i in 0 until arrayApi.length()) {
                    val jsonObj = arrayApi.getJSONObject(i)
                    val user = User(
                        jsonObj.getInt("id"),
                        jsonObj.getString("email"),
                        jsonObj.getString("first_name"),
                        jsonObj.getString("last_name"),
                        jsonObj.getString("avatar")
                    )
                    users.add(user)
                }
                callback.onUsersLoaded(users) // Devuelve los usuarios
            },
            { err -> // Error
                Log.e("API_ERROR", "Error: ${err.message}") // Agrega esto para ver el error en el Logcat
                err.printStackTrace()
            }
        )

        queue.add(jsonObjReq)
    }
}

interface UserCallback {
    fun onUsersLoaded(users: List<User>)
}


