package io.nekohasekai.sagernet.ui.dns

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.nekohasekai.sagernet.R
import io.nekohasekai.sagernet.database.DnsServerEntity

class DnsServerAdapter(
    private val items: List<DnsServerEntity>,
    private val onItemClick: (DnsServerEntity) -> Unit,
) : RecyclerView.Adapter<DnsServerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tagView: TextView = view.findViewById(R.id.title)
        val addressView: TextView = view.findViewById(R.id.summary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_dns_server, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tagView.text = item.tag
        holder.addressView.text = "${item.address} (${item.type})"
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = items.size
}
