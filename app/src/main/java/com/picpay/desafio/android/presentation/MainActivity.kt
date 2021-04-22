package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.observeResource
import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.presentation.ui.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val viewModel: GetUsersViewModel by viewModel()
    private val adapter by lazy {
        UserListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        initObserver()
    }

    private fun initUi() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)
        recyclerView.adapter = adapter
    }

    private fun initObserver() {
        viewModel.usersLiveData.observeResource(this,
            onSuccess = { users -> showSuccess(users) },
            onError = { showError() },
            onLoading = { showLoading() }
        )
    }

    private fun showError() {
        val message = getString(R.string.error)

        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE

        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showSuccess(users: List<UserDomain>) {
        progressBar.visibility = View.GONE
        adapter.users = users
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }
}
