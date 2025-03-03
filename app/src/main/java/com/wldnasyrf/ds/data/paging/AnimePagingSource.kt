package com.wldnasyrf.ds.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wldnasyrf.ds.data.remote.api.ApiService
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData

class AnimePagingSource(private val apiService: ApiService): PagingSource<Int, AnimeData>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeData> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAnime(params.loadSize, position)

            val animeList = responseData.data?.data ?: emptyList()

            LoadResult.Page(
                data = animeList,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if (animeList.isEmpty()) null else position+1
            )

        }  catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
