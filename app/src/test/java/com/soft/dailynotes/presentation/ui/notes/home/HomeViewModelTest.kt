package com.soft.dailynotes.presentation.ui.notes.home

import com.soft.dailynotes.data.repositoryImp.FakeArchiveRepositoryImp
import com.soft.dailynotes.data.repositoryImp.FakeNotesOperator
import com.soft.dailynotes.data.repositoryImp.FakeNotesRepository
import com.soft.dailynotes.data.repositoryImp.FakePreferenceRepositoryImp
import com.soft.dailynotes.data.repositoryImp.FakeWorkRepositoryImp
import com.soft.dailynotes.presentation.ui.notes.MainRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val testDispatcher = MainRule()
    private lateinit var homeViewModel: HomeViewModel
      private  lateinit var  fakepref:FakePreferenceRepositoryImp



    @Before
    fun setUp() {
        fakepref =FakePreferenceRepositoryImp()
        homeViewModel = HomeViewModel(
            notesRepository = FakeNotesRepository(),
            notificationRepository = FakeWorkRepositoryImp(),
            archiveRepository = FakeArchiveRepositoryImp(),
            notesOperatorsPlan = FakeNotesOperator(),
            preferenceRepository = fakepref
        )
    }

    @Test
    fun saveLayoutStatus(){
        runBlocking {
            homeViewModel.preferenceRepository.saveHomeLayoutPreference(true)

            assertEquals(true,fakepref.savedValueIsHome)
        }


    }
    @Test
    fun getLayoutStatus(){
        runBlocking{
            homeViewModel.preferenceRepository.saveHomeLayoutPreference(false)


            assertEquals(false,fakepref.savedValueIsHome)
        }

    }
}
