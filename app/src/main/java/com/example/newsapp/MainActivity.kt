package com.example.newsapp

import android.app.DownloadManager
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import androidx.browser.customtabs.CustomTabColorSchemeParams




class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: MyNewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView.layoutManager=LinearLayoutManager(this)
        fetchData()
       mAdapter= MyNewsAdapter(this)
        recyclerView.adapter=mAdapter

    }
    fun fetchData(){
        val url= "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=24e0963078ea40c0951864d0b0050f20"
        val jsonObjectRequest = object : JsonObjectRequest(    // Added the object
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }


                mAdapter.updateNews(newsArray)


            },
            Response.ErrorListener {

                    Toast.makeText(this,"something went wrong",Toast.LENGTH_SHORT).show()
            }

            //Included the header here
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun OnItemClicked(item: News) {

       val builder= CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
        val colorInt: Int = Color.parseColor("#000000") //red

        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(colorInt)
            .build()
        builder.setDefaultColorSchemeParams(defaultColors)
    }
}