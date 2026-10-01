package io.nekohasekai.sagernet.ui.dns

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.nekohasekai.sagernet.R
import io.nekohasekai.sagernet.database.SagerDatabase
import io.nekohasekai.sagernet.ui.ThemedActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DnsServerListActivity : ThemedActivity(R.layout.layout_dns_server_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DnsServerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setTitle(R.string.dns_servers_title)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_navigation_close)
        }

        recyclerView = findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DnsServerAdapter(emptyList()) { server ->
            startActivity(Intent(this, DnsServerEditActivity::class.java).putExtra("server_id", server.id))
        }
        supportFragmentManager.setFragmentResultListener("refresh", this) { _, _ -> loadServers() }
        loadServers()
    }

    private fun loadServers() {
        lifecycleScope.launch {
            val servers = withContext(Dispatchers.IO) { SagerDatabase.instance.dnsServerDao().list() }
            adapter = DnsServerAdapter(servers) { server ->
                startActivity(Intent(this@DnsServerListActivity, DnsServerEditActivity::class.java).putExtra("server_id", server.id))
            }
            recyclerView.adapter = adapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> { finish(); true }
        else -> super.onOptionsItemSelected(item)
    }
}
