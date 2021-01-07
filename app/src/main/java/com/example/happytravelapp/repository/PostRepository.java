package com.example.happytravelapp.repository;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.Comment;
import com.example.happytravelapp.model.Post;
import com.example.happytravelapp.model.User;
import com.example.happytravelapp.ultil.Common;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostRepository {
    private static final String TAG = "Postrepository";
    private DatabaseReference post_ref;
    private DatabaseReference post_comments_ref;

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<Boolean> postCreatIsSuccessfull;
    private MutableLiveData<String> imageUrl;
    private MutableLiveData<Boolean> imageUpdateIsSuccessful;
    static private List<Post> mListPost;
    static private List<Comment> mListComment;

    public MutableLiveData<String> getImageUrl() {
        return imageUrl;
    }

    public MutableLiveData<Boolean> getImageUpdateIsSuccessful() {
        return imageUpdateIsSuccessful;
    }

    public MutableLiveData<Boolean> getPostCreatIsSuccessfull() {
        return postCreatIsSuccessfull;
    }

    public PostRepository() {
        post_ref = FirebaseDatabase.getInstance().getReference("/posts");
        post_comments_ref = FirebaseDatabase.getInstance().getReference("/post-comments");
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.imageUrl = new MutableLiveData<>();
        this.imageUpdateIsSuccessful = new MutableLiveData<>();
        postCreatIsSuccessfull = new MutableLiveData<>();
        mListPost=new ArrayList<>();
        mListComment=new ArrayList<>();
    }

    public void writeNewPost(String title, String body, String imageUrl) {
        String key = post_ref.push().getKey();
        String userId = firebaseAuth.getUid();
        String postid=key;
        String author= Common.FULLNAME;
        String profilePic=Common.IMAGE_URL;
        Post post = new Post(userId,postid, author, profilePic,title, body, imageUrl, ServerValue.TIMESTAMP,0,0);

        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
        postCreatIsSuccessfull.setValue(true);
    }

    public String uploadImage(Intent intentData) {
        Uri selectedUri = intentData.getData();
        StorageReference photoRef = FirebaseStorage.getInstance()
                .getReference().child("users")
                .child(selectedUri.getLastPathSegment());

        UploadTask uploadTask = photoRef.putFile(selectedUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return photoRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    imageUrl.setValue(String.valueOf(downloadUri));
                    imageUpdateIsSuccessful.setValue(true);
                } else {
                    imageUpdateIsSuccessful.setValue(false);
                }
            }
        });
        return imageUrl.getValue();
    }

    public void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post p = mutableData.getValue(Post.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(Common.USER_ID)) {

                    p.starCount = p.starCount - 1;
                    p.stars.remove(Common.USER_ID);
                } else {

                    p.starCount = p.starCount + 1;
                    p.stars.put(Common.USER_ID, true);
                }

                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {

                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }


    public void postComment(String postKey,String commentText) {
        final String uid = Common.USER_ID;
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        String authorName = user.getFullname();
                        String authorPic=Common.IMAGE_URL;
                        Comment comment = new Comment(uid, authorName,authorPic, commentText);
                        post_comments_ref.child(postKey).push().setValue(comment);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    public static class DeserializerPostList implements Function<DataSnapshot, List<Post>> {
        @Override
        public List<Post> apply(DataSnapshot dataSnapshot) {
            mListPost.clear();
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                Post post = snap.getValue(Post.class);
                mListPost.add(post);
            }
            return mListPost;
        }
    }

    public static class DeserializerCommentList implements Function<DataSnapshot, List<Comment>> {
        @Override
        public List<Comment> apply(DataSnapshot dataSnapshot) {
            mListComment.clear();
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                Comment comment = snap.getValue(Comment.class);
                mListComment.add(comment);
            }
            return mListComment;
        }
    }
}
