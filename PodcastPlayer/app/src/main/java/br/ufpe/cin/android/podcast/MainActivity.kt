package br.ufpe.cin.android.podcast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doAsync {
            DownloadRssFeed()

            val itemFeeds =
                ItemFeedDB.getDatabase(applicationContext).ItemFeedDAO().getAllItemFeeds()
            uiThread {
                LoadUi(itemFeeds)
            }
        }
    }

    fun DownloadRssFeed() {
        var xmlString: String?
        try {
            xmlString = URL(getString(R.string.feed_xml_link)).readText()
        } catch (e: Exception) {
            return
        }
        val parsedXml = Parser.parse(xmlString)
        parsedXml.forEach {
            ItemFeedDB.getDatabase(applicationContext).ItemFeedDAO().insertItemFeeds(it)
        }
    }

    fun LoadUi(itemFeeds: List<ItemFeed>) {
        xmlDataView.layoutManager = LinearLayoutManager(this@MainActivity)
        xmlDataView.adapter = xmlDataViewAdapter(itemFeeds)
        xmlDataView.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                LinearLayoutManager.VERTICAL
            )
        )
    }
}
