package com.jwhh.notekeeper.ui.home;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jwhh.notekeeper.DataManager;
import com.jwhh.notekeeper.NoteInfo;
import com.jwhh.notekeeper.NoteKeeperOpenHelper;
import com.jwhh.notekeeper.NoteRecyclerAdaptar;
import com.jwhh.notekeeper.R;

import java.util.List;

public class HomeFragment extends Fragment {
    private NoteRecyclerAdaptar mNoteRecyclerAdaptar;
    private RecyclerView mRecyclerNotes;
    private LinearLayoutManager mNotesLayoutManager;
    private NoteKeeperOpenHelper mDbOpenHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mDbOpenHelper = new NoteKeeperOpenHelper(getContext());

        mRecyclerNotes = (RecyclerView)root.findViewById(R.id.list_items);
        mNotesLayoutManager = new LinearLayoutManager(getContext());

        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        mNoteRecyclerAdaptar = new NoteRecyclerAdaptar(getContext(), notes);

        displayNotes();
        return root;
    }

    @Override
    public void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    public void displayNotes() {
        mRecyclerNotes.setLayoutManager(mNotesLayoutManager);
        mRecyclerNotes.setAdapter(mNoteRecyclerAdaptar);

        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        mNoteRecyclerAdaptar.notifyDataSetChanged();
    }
}
