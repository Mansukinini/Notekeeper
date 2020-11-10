package com.jwhh.notekeeper.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jwhh.notekeeper.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        final TextView textView = root.findViewById(R.id.textView);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}

//    View root = inflater.inflate(R.layout.fragment_home, container, false);
//
//    final RecyclerView recyclerNotes = (RecyclerView)root.findViewById(R.id.list_items);
//    final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(getContext());
//        recyclerNotes.setLayoutManager(notesLayoutManager);
//
//                List<NoteInfo> notes = DataManager.getInstance().getNotes();
//        mNoteRecyclerAdaptar = new NoteRecyclerAdaptar(getContext(), notes);
//        recyclerNotes.setAdapter(mNoteRecyclerAdaptar);
//
//        return root;