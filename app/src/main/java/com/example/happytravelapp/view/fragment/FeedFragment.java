package com.example.happytravelapp.view.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.adapter.PostAdapter;
import com.example.happytravelapp.databinding.FragmentFeedBinding;
import com.example.happytravelapp.databinding.FragmentHomeBinding;
import com.example.happytravelapp.model.Post;
import com.example.happytravelapp.ultil.Common;
import com.example.happytravelapp.view.CreatNewFeedActivity;
import com.example.happytravelapp.viewmodel.PostViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    FragmentFeedBinding binding;
    PostViewModel postViewModel;

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postViewModel= ViewModelProviders.of(this).get(PostViewModel.class);

        initRecycleview();

        binding.newfeed.setOnClickListener(v->{
            startActivity(new Intent(getContext(), CreatNewFeedActivity.class));
        });

    }

    public void initRecycleview(){
        postViewModel.getPostListLiveData().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                PostAdapter adapter=new PostAdapter(posts, new PostAdapter.ItemLikeClickListener() {
                    @Override
                    public void onClick(Post post) {
                        postViewModel.onPostLikeClicked(post);
                        postViewModel.onUserPostLikeClicked(post);
                    }
                }, new PostAdapter.ItemCommentClickListener() {
                    @Override
                    public void onClick(Post post) {
                        Common.POSTID=post.getPostid();
                        showBottomSheetDialogFragment();
                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                binding.recyclerview.setLayoutManager(layoutManager);
                binding.recyclerview.setAdapter(adapter);
            }
        });
    }

    public void showBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_comment_dialog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(view);
        dialog.show();
    }

    public void showBottomSheetDialogFragment() {
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.show(getFragmentManager(), commentFragment.getTag());
    }
}
