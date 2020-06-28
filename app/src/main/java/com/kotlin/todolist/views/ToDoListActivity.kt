package com.kotlin.todolist.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.todolist.R
import com.kotlin.todolist.model.ApiResponse
import com.kotlin.todolist.model.Todos
import com.kotlin.todolist.util.InternetNetwork
import com.kotlin.todolist.viewmodel.TodosViewModel
import com.kotlin.todolist.views.adapter.TodosAdapter
import com.test.diwakarsinghtest.view.NoInternetConnectionFragment
import com.test.diwakarsinghtest.view.SimpleCallBack
import kotlinx.android.synthetic.main.activity_to_do_list.*
import org.jetbrains.annotations.TestOnly


class ToDoListActivity : AppCompatActivity(), SimpleCallBack {

    private lateinit var viewModel: TodosViewModel
    private val FRAG_TAG = "NoInternetFrag"
    private var sortValue = false
    private var todoadapter: TodosAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        initViews()
        initViewModel()
        swipeRefreshListener()
        checkInternetAndFetchData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(TodosViewModel::class.java)
    }

    private fun initViews() {
        //  set menu.xml to custom toolbar
        toolbar.inflateMenu(R.menu.menu)
        // support of clicking on menu items
        setSupportActionBar(toolbar)
    }

    // ** check internet connection before fetching data
    private fun checkInternetAndFetchData() {
        if (InternetNetwork.isOnline(this)) {
            fetchData()
        } else {
            showNoInternetFragment(true)
        }
    }

    // ** fetch data from url
    private fun fetchData() {
        shimmer_view_container.startShimmerAnimation()
        viewModel.getTodos().observe(this,
            Observer { response ->
                when (response) {
                    is ApiResponse.Error -> showNoInternetFragment(false)
                    is ApiResponse.TodosList -> {
                        if (sortValue) {
                            setDataInAdapter(response.todolist.sortedBy { it.title })
                        } else {
                            setDataInAdapter(response.todolist)
                        }
                    }
                }
            })
    }

    private fun setDataInAdapter(data: List<Todos>?) {
        todoadapter = TodosAdapter(data)
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = todoadapter
        recycler_view.setHasFixedSize(true)
        shimmer_view_container.stopShimmerAnimation()
        shimmer_view_container.visibility = View.GONE
    }


    // ** no internet connection layout
    fun showNoInternetFragment(fragTagCondition: Boolean) {
        shimmer_view_container.stopShimmerAnimation()
        container.visibility = View.INVISIBLE
        shimmer_view_container.visibility = View.VISIBLE
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.shimmer_view_container,
                NoInternetConnectionFragment(fragTagCondition),
                FRAG_TAG
            )
            .commit()
    }

    // ** swipe listener for fetching new data and update the list
    private fun swipeRefreshListener() {
        swipe_refresh_layout.setOnRefreshListener {
            onReload()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // ** click listener of items in menu options and update list acc to it
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_names -> {
                sortValue = true
                fetchData()
                true
            }
            R.id.sort_by_default -> {
                sortValue = false
                fetchData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ** remove no internet fragment if internet connection is available
    private fun removeNoInternetFragment() {
        val fragment = supportFragmentManager.findFragmentByTag(FRAG_TAG)
        if (fragment != null) supportFragmentManager.beginTransaction().remove(fragment).commit()
    }

    // ** retry button listener from no internet connection layout
    override fun onReload() {
        removeNoInternetFragment()
        if (container.visibility == View.INVISIBLE) {
            container.visibility = View.VISIBLE
        }
        checkInternetAndFetchData()
        todoadapter?.notifyDataSetChanged()
        swipe_refresh_layout.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs() //to cancel the job if anything is in progress
    }

    @TestOnly
    fun setTestViewModel(testViewModel: TodosViewModel) {
        viewModel = testViewModel
    }


}