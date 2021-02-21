package com.example.stonks.listStocks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stonks.R
import com.example.stonks.databinding.ListItemBinding
import com.example.stonks.network.MarsProperty
import com.example.stonks.network.PriceInfo
import com.example.stonks.network.StockInfo

class MyAdapter(val prices:List<LiveData<PriceInfo>>,val lifecycleOwner: LifecycleOwner):ListAdapter<StockInfo,RecyclerView.ViewHolder>(REPO_COMPARATOR){
    class ViewHolder private constructor(val binding: ListItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun Double.toText():String{
            return if(this >= 0) this.toString() else ""
        }
        fun bind(item: StockInfo, price:LiveData<PriceInfo>, lifecycleOwner: LifecycleOwner) {
            binding.pic = item
            val url = "https://finnhub.io/api/logo?symbol=${item.companyInfo.symbol}"
            Glide.with(binding.imageView.context)
                .load(url)
                .error(R.drawable.loading_animation)
                .into(binding.imageView)
            binding.textView3.text = price.value?.currentPrice?.toText() ?: ""
            binding.textView4.text = price.value?.openPrice?.toText() ?: ""
            price.observe(lifecycleOwner){
                binding.textView3.text = it.currentPrice.toText()
                binding.textView4.text = it.openPrice.toText()
                binding.executePendingBindings()
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, parent.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as ViewHolder).bind(repoItem,prices[position],lifecycleOwner)
        }
    }
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<StockInfo>() {
            override fun areItemsTheSame(oldItem: StockInfo, newItem: StockInfo): Boolean =
                oldItem.companyInfo.symbol == newItem.companyInfo.symbol && oldItem.companyInfo.description == newItem.companyInfo.description

            override fun areContentsTheSame(oldItem: StockInfo, newItem: StockInfo): Boolean =
                oldItem.priceInfo.currentPrice == newItem.priceInfo.currentPrice && oldItem.priceInfo.openPrice == newItem.priceInfo.openPrice
        }
    }

}