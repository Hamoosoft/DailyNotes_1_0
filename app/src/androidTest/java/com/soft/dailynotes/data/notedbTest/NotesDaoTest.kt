package com.soft.dailynotes.data.notedbTest

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.soft.dailynotes.data.notedb.NotesDao
import com.soft.dailynotes.data.notedb.NotesDb
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@RunWith(AndroidJUnit4::class)
@SmallTest
@HiltAndroidTest

class NotesDaoTest {
  @get:Rule
  var hiltTestRul = HiltAndroidRule(this)

    @Inject
    @Named("note_db")
     lateinit var db: NotesDb
     lateinit var notesDao: NotesDao

    @Before
    fun setUp() {

       hiltTestRul.inject()

        notesDao = db.notesDao()

    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun insert_note_into_notesDao_with_the_assertion_true(){

       runBlocking {

           notesDao.insetNote(FakeDataBase.notes[0])

           var result = notesDao.retrieveNote(1)

           assertEquals(FakeDataBase.notes[0],result.first())
       }
    }

    @Test
    fun insert_note_into_notesDao_with_the_assertion_false(){

        runBlocking {

            notesDao.insetNote(FakeDataBase.notes[0])

            var result = notesDao.retrieveNote(1)

            assertNotEquals(FakeDataBase.notes[1],result.first())
        }
    }

    @Test
    fun update_notesDao_with_the_assertion_true(){

        runBlocking {

            notesDao.insetNote(FakeDataBase.notes[0])

            notesDao.updateNote(FakeDataBase.notes[3])

            var result = notesDao.retrieveNote(1)

            assertEquals(FakeDataBase.notes[3],result.first())
        }
    }

    @Test
    fun update_notesDao_with_the_assertion_false(){

        runBlocking {

            notesDao.insetNote(FakeDataBase.notes[0])

            notesDao.updateNote(FakeDataBase.notes[3])

            var result = notesDao.retrieveNote(1)

            assertNotEquals(FakeDataBase.notes[0],result.first())
        }
    }

    @Test
    fun retrieve_all_from_notesDao_with_the_assertion_true(){

        runBlocking {

            notesDao.insetNote(FakeDataBase.notes[0])
            notesDao.insetNote(FakeDataBase.notes[1])
            notesDao.insetNote(FakeDataBase.notes[2])

            val result = notesDao.retrieveAllNotes().first()


            assertNotEquals(FakeDataBase.notes,result)
        }
    }

    @Test
    fun delete_note_from_notesDao_with_the_assertion_true(){

        runBlocking {

            notesDao.insetNote(FakeDataBase.notes[0])
            notesDao.insetNote(FakeDataBase.notes[1])

            notesDao.deleteNote(FakeDataBase.notes[0])

            val result = notesDao.retrieveAllNotes().first()


            assertNotEquals(FakeDataBase.notes[0],result[0])
        }
    }

    @Test
    fun retrieve_note_from_notesDao_by_id_with_the_assertion_true(){

        runBlocking {

            notesDao.insetNote(FakeDataBase.notes[0])
            notesDao.insetNote(FakeDataBase.notes[1])

            val result = notesDao.retrieveNote(FakeDataBase.notes[0].noteId).first()


            assertEquals(FakeDataBase.notes[0].title,result.title)
        }
    }
    @Test
    fun retrieve_note_from_notesDao_by_id_with_the_assertion_false(){

        runBlocking {

            notesDao.insetNote(FakeDataBase.notes[0])
            notesDao.insetNote(FakeDataBase.notes[1])

            val result = notesDao.retrieveNote(FakeDataBase.notes[0].noteId).first()


            assertNotEquals(FakeDataBase.notes[1].title,result.title)
        }
    }
}