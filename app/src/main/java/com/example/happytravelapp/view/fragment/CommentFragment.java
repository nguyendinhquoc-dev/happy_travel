package com.example.happytravelapp.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happytravelapp.adapter.CommentAdapter;
import com.example.happytravelapp.databinding.FragmentBottomSheetCommentDialogBinding;
import com.example.happytravelapp.model.Comment;
import com.example.happytravelapp.viewmodel.PostViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends BottomSheetDialogFragment {
    FragmentBottomSheetCommentDialogBinding binding;
    PostViewModel postViewModel;


    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetCommentDialogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postViewModel= ViewModelProviders.of(this).get(PostViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setPostVM(postViewModel);

        initRecycleview();

    }

    public void initRecycleview() {
        postViewModel.getCommentListLiveData().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                if(comments!=null) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    CommentAdapter adapter = new CommentAdapter(comments);
                    binding.recyclerview.setLayoutManager(layoutManager);
                    binding.recyclerview.setAdapter(adapter);
                }
            }
        });
    }
}
