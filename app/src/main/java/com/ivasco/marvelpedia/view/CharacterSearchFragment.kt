package com.ivasco.marvelpedia.view

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search.view.*
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ivasco.marvelpedia.R
import com.ivasco.marvelpedia.api.paging.CharactersDataSource
import com.ivasco.marvelpedia.viewmodel.CharactersViewModel
import com.ivasco.marvelpedia.model.Character
import com.ivasco.marvelpedia.view.adapter.CharacterRecycleViewAdapter

private const val GRID_SPAN_COUNT = 3

class CharacterSearchFragment : Fragment(), ISearchFragment {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharacterRecycleViewAdapter by lazy {
        CharacterRecycleViewAdapter { character -> onItemClicked(character) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        setUpRecycleView(rootView)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = menu.findItem(R.id.navigation_search)?.actionView as SearchView
        searchView.isIconified = true
    }

    //TODO
    private fun onItemClicked(character: Character) {
    }

    private fun setUpRecycleView(rootView: View) {
        CharactersDataSource.search = null
        val layoutManager = GridLayoutManager(activity, GRID_SPAN_COUNT)
        val recyclerView = rootView.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        subscribeToList()
    }

    private fun subscribeToList() {
        val test = viewModel.characterList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                adapter.submitList(list)
            }
    }

    override fun makeSearch(search: String?) {
        CharactersDataSource.search = search
        adapter.currentList?.dataSource?.invalidate()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharacterSearchFragment()
    }
}