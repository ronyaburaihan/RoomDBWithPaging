package com.example.roomdbwithpaging.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.roomdbwithpaging.data.database.ContactDao
import com.example.roomdbwithpaging.data.model.ContactItem

class ContactPagingSource(private val contactDao: ContactDao) : PagingSource<Int, ContactItem>() {

    override fun getRefreshKey(state: PagingState<Int, ContactItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ContactItem> {
        val page = params.key ?: 0

        return try {
            val entities = contactDao.getContactList(params.loadSize, page * params.loadSize)
            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
