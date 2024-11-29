package co.com.sena.proyectos.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.sena.proyectos.recyclerview.databinding.ItemListBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class UserAdapter(private val users: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() //->Esto es un constructor
{
    // -> Context: Son las configuraciones base de la lógica que se vincula con la vista.
    // Lateinit: Creamos el dato para almacenar memoria, lluego va a inicializarlo
    private lateinit var context: Context
    //control + i

    //(añade, vincula, infla) las configuraciones de la vista (item_list) y el xml
    // de la vista (R.layout.item_list.xml), y retorna a la etiqueta de RecyclerView (activity_main)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //El parent viene de la vista del ViewHolder ^
        context = parent.context
        //Inflar o expandir la app
        //-> false no hay componentes externos o más configuraciones
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    //Asignar los valores a la vista (Estructura la DATA)
    //Añade los campos de la vista vinculada (item_list)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val user = users.get(position)
        val user = users[position]

        with(holder.binding){
            tvName.text = user.getFullName()
            tvOrden.text = (position + 1).toString()

            //-> Configuraciones básicas entre la vista y la lógica +más para la lógica
            Glide.with(context)
                .load(user.avatar)
                //Crentrar imágen en la mitad. Independiente del tamaño
                .centerCrop()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPhoto)
        }
    }
    //Se determina cuántas veces se repetirá la vista vinculada (item_list) desde users
    override fun getItemCount(): Int = users.size

    //Adaptador: Adminstra y controla los datos, vinculandolos con una vista o información, como una imágen.

    //->Vinculacion entre el adaptador y la vista que se va a reciclar(item_list), y el adaptador
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        //->Creamos vínculo
        var binding = ItemListBinding.bind(view)
    }
}