package polinema.mi.ghifari_prak13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var mhsAdapter : AdapterDataMhs
    var daftarMhs = mutableListOf<HashMap<String, String>>()
    var url = "http://192.168.1.17:8000/api/mahasiswa"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mhsAdapter = AdapterDataMhs(daftarMhs)
        listMhs.layoutManager = LinearLayoutManager(this)
        listMhs.adapter = mhsAdapter
        showDataMhs()

    }

    fun showDataMhs(){
        val request = StringRequest(Request.Method.POST,url,
            Response.Listener { response ->
                val jsonobject = JSONObject(response)
                val jsonArray = JSONArray(jsonobject.getString("data"))
                for (x in 0 .. (jsonArray.length()-1)){
                    val jsonObject = jsonArray.getJSONObject(x)
                    var mhs = HashMap<String,String>()
                    mhs.put("nim",jsonObject.getString("nim"))
                    mhs.put("nama",jsonObject.getString("nama"))
                    mhs.put("nama_prodi",jsonObject.getString("nama_prodi"))
                    mhs.put("url",jsonObject.getString("url"))
                    mhs.put("alamat",jsonObject.getString("alamat"))
                    daftarMhs.add(mhs)
                }
                mhsAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Terjadi kesalahan koneksi ke server",Toast.LENGTH_LONG).show()
            })
        val queue = Volley.newRequestQueue(this)
        queue.add(request)

    }

}