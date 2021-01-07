package com.example.happytravelapp.viewmodel;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.widget.SearchView;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.firebase.MyFirebaseMessagingService;
import com.example.happytravelapp.model.Comment;
import com.example.happytravelapp.model.Post;
import com.example.happytravelapp.repository.PostRepository;
import com.example.happytravelapp.ultil.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PostViewModel extends ViewModel {
    private DatabaseReference post_ref= FirebaseDatabase.getInstance().getReference("posts");
    private DatabaseReference user_post_ref= FirebaseDatabase.getInstance().getReference("user-posts");
    private DatabaseReference post_comments_ref= FirebaseDatabase.getInstance().getReference("post-comments").child(Common.POSTID);
    private FirebaseQueryLiveData liveData1 = new FirebaseQueryLiveData(post_ref);
    private FirebaseQueryLiveData liveData2 = new FirebaseQueryLiveData(post_comments_ref);

    private PostRepository postRepository;
    private MutableLiveData<Boolean> postCreatIsSuccessfull;
    private MutableLiveData<Boolean> imageUpdateIsSuccessful;
    public MutableLiveData<String> imageUrl = new MutableLiveData<>();
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> body = new MutableLiveData<>();
    public MutableLiveData<String> titleError = new MutableLiveData<>();
    public MutableLiveData<String> bodyError = new MutableLiveData<>();
    public MutableLiveData<String> commmentText = new MutableLiveData<>();

    private final LiveData<List<Post>> postListLiveData = Transformations.map(liveData1, new PostRepository.DeserializerPostList());
    private final LiveData<List<Comment>> commentListLiveData = Transformations.map(liveData2, new PostRepository.DeserializerCommentList());

    public LiveData<List<Post>> getPostListLiveData() {
        return postListLiveData;
    }

    public LiveData<List<Comment>> getCommentListLiveData() {
        return commentListLiveData;
    }

    public MutableLiveData<Boolean> getPostCreatIsSuccessfull() {
        return postCreatIsSuccessfull;
    }

    public PostViewModel() {
        super();
        postRepository = new PostRepository();
        this.imageUrl = postRepository.getImageUrl();
        postCreatIsSuccessfull = postRepository.getPostCreatIsSuccessfull();
        this.imageUpdateIsSuccessful = postRepository.getImageUpdateIsSuccessful();
    }

    public void writeNewPost() {
        if (validator() == true) {
            postRepository.writeNewPost(title.getValue(), body.getValue(), imageUrl.getValue());
        }
    }

    public void uploadImage(Intent intentData) {
        postRepository.uploadImage(intentData);
    }

    public void onPostLikeClicked(Post post){
        postRepository.onStarClicked(post_ref.child(post.getPostid()));
        Log.d("postref",post_ref.child(post.getPostid()).toString());
    }

    public void onUserPostLikeClicked(Post post){
        postRepository.onStarClicked(user_post_ref.child(post.uid).child(post.getPostid()));
        Log.d("postref",user_post_ref.child(post.uid).child(post.getPostid()).toString());
    }

    public void postComment(String postId){
        postRepository.postComment(postId,commmentText.getValue());
    }


    public boolean validator() {
        Boolean flag = true;
        if (title.getValue() == null || title.getValue().isEmpty()) {
            titleError.setValue("Required");
            flag = false;
        }
        if (body.getValue() == null || body.getValue().isEmpty()) {
            bodyError.setValue("Required");
            flag = false;
        }

        return flag;
    }

}
