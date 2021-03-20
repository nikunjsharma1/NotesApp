package com.example.mynotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_notes.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class AddNotesFragment : BaseFragment() {

    var not: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_notes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            not = AddNotesFragmentArgs.fromBundle(it).note
            name.setText(not?.title)
            note.setText(not?.note)
        }
        done.setOnClickListener {view ->
            val noteTitle=name.text.toString().trim()
            val noteBody=note.text.toString().trim()
            if(noteTitle.isEmpty()){
                name.error="title required"
                name.requestFocus()
                return@setOnClickListener
            }
            if(noteBody.isEmpty()){
                note.error="title required"
                note.requestFocus()
                return@setOnClickListener
            }

           launch {

              context?.let {
                  val mnote=Note(noteTitle,noteBody)
                  if (not==null){
                      NoteDatabase(it).getNoteDao().addNote(mnote)

                  }else{
                      mnote.id=not!!.id
                      NoteDatabase(it).getNoteDao().updateNote(mnote)
                  }

                  val action = AddNotesFragmentDirections.actionAddNotesFragment2ToHomeFragment2()
                  Navigation.findNavController(view).navigate(action)
              }
           }
        }
    }

}