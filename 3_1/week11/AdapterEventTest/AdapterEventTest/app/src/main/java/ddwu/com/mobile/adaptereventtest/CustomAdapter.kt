package ddwu.com.mobile.adaptereventtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val weathers: ArrayList<WeatherDto>):
    RecyclerView.Adapter<CustomAdapter.WeatherViewHolder>() {

    override fun getItemCount(): Int = weathers.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_weather, parent, false)
        return CustomAdapter.WeatherViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.no.text = weathers[position].no
        holder.dong.text = weathers[position].dong
        holder.loc.text = weathers[position].loc
        holder.weather.text = weathers[position].weather
    }

    class WeatherViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val no = view.findViewById<TextView>(R.id.tvNo)
        val dong = view.findViewById<TextView>(R.id.tvDong)
        val loc = view.findViewById<TextView>(R.id.tvLoc)
        val weather = view.findViewById<TextView>(R.id.tvWeather)
    }
}