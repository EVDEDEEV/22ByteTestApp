package my.project.a22bytetestapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import my.project.a22bytetestapp.R
import my.project.a22bytetestapp.databinding.ActivityMainBinding
import my.project.a22bytetestapp.presentation.adapters.NewsAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val newsAdapter by lazy {
        NewsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL, false
            )
            val itemDecoration =
                DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            AppCompatResources.getDrawable(this@MainActivity,
                R.drawable.recycler_divider)?.let {
                itemDecoration.setDrawable(it)
            }
            addItemDecoration(itemDecoration)
            setHasFixedSize(true)
        }
    }

    private fun initViewModel() {
        lifecycleScope.launchWhenCreated {
            mainViewModel.getNews().collectLatest {
                newsAdapter.submitData(it)
            }
        }
    }
}