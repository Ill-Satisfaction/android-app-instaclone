package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class FeedFragment extends Fragment {
    private static String TAG = "FeedFragment";
    private static int NUM_POSTS_TO_LOAD = 20;

    private View root;
    private Activity parent;
    private List<Post> feed;

    RecyclerView recyclerView;
    FeedAdapter feedAdapter;
    SwipeRefreshLayout swipeContainer;

    public FeedFragment() {} // Required empty public constructor
    public FeedFragment(Activity context) {
        this.parent = context;
    }

    public static FeedFragment newInstance(String param1, String param2) {
        return new FeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_feed, container, false);
        feed = new ArrayList<>();

        swipeContainer = root.findViewById(R.id.sc_feed_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
            }
        });

        recyclerView = root.findViewById(R.id.rv_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        feedAdapter = new FeedAdapter(root.getContext());
        recyclerView.setAdapter(feedAdapter);
        queryPosts();



        return root;
    }

    public void queryPosts () {
        Log.i(TAG, "got here");
        //feed = new LinkedList<>();
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
                    if (counter <= NUM_POSTS_TO_LOAD) newPosts.add(p);
                    else break;
                    counter++;
                }
                feedAdapter.addAll(newPosts);
                feedAdapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
    }
}