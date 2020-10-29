package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.Stack;

public class ProfileFragment extends Fragment {

    private static String TAG = "ProfileFragment";
    private static int NUM_POSTS_TO_LOAD = 20;

    Button btnLogout;
    RecyclerView rvProfile;
    FeedAdapter feedAdapter;
    private List<Post> feed;

    public ProfileFragment() {} // Required empty public constructor

    public static ProfileFragment newInstance(String param1, String param2) {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);
        btnLogout = root.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(root);
            }
        });

        feed = new Stack<Post>();
        rvProfile = root.findViewById(R.id.rv_profile);
        rvProfile.setLayoutManager(new LinearLayoutManager(root.getContext()));
        feedAdapter = new FeedAdapter(root.getContext());
        rvProfile.setAdapter(feedAdapter);
        queryPosts();



        return root;
    }

    private void queryPosts() {
        Log.i(TAG, "got here");
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "issue with getting posts", e);
                    return;
                }
                Stack<Post> newPosts = new Stack<>();
                int counter = 0;
                for (Post p : posts) {
                    if (counter <= NUM_POSTS_TO_LOAD) {
                        if (p.getUser().getUsername().equals(ParseUser.getCurrentUser().getUsername())) {
                            newPosts.add(p);
                            counter++;
                        }
                    }
                    else break;
                }
                feedAdapter.addAll(newPosts);
                feedAdapter.notifyDataSetChanged();
            }
        });
    }

    private void logout (View v) {
        // log out
        ParseUser.logOutInBackground();
        // move to login screen
        Intent i = new Intent(v.getContext(), LoginActivity.class);
        startActivity(i);
    }
}