package com.wldnasyrf.ds.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wldnasyrf.ds.data.remote.api.ApiService
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData

class AnimePagingSource(private val apiService: ApiService,
                        private val category: String? = null
): PagingSource<Int, AnimeData>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeData> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAnime(
                size = 10,
                page = page,
                category = category
            )

            val animeList = responseData.data?.data ?: emptyList()

            LoadResult.Page(
                data = animeList,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page -1,
                nextKey = if (animeList.isEmpty()) null else page+1
            )

        }  catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
