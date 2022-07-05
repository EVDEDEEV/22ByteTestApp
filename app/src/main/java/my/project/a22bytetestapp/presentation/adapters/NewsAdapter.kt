package my.project.a22bytetestapp.presentation.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import my.project.a22bytetestapp.R
import my.project.a22bytetestapp.databinding.NewsItemBinding
import my.project.a22bytetestapp.presentation.models.News

class NewsAdapter : PagingDataAdapter<News, NewsAdapter.MyViewHolder>(DiffUtilCallBack) {

    object DiffUtilCallBack : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: News,
            newItem: News,
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class MyViewHolder(
        private val binding: NewsItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News) {
            if (news.image.isEmpty()) {
                binding.imageView.setImageResource(R.drawable.local_news)
            } else {
                Picasso.get().load(news.image).fit().centerCrop().into(binding.imageView)
            }
            binding.source.text = news.source
            binding.news.text = news.title
            binding.root.setOnClickListener {
                val position = absoluteAdapterPosition
                val newsItem = getItem(position)
                if (newsItem != null) {
                    openNewsArticleInChrome(newsItem.url, itemView.context)
                }
            }
        }

//        init {
//            binding.apply {
//                root.setOnClickListener {
//                    val position = absoluteAdapterPosition
//                    val newsItem = getItem(position)
//                    if (newsItem != null) {
//                        openNewsArticleInChrome(newsItem.url, itemView.context)
//                    }
//                }
//            }
//        }


                private fun openNewsArticleInChrome(url: String, context: Context) {
                    val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse(url))
                }
            }
        }



