package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.jokedisplaylibrary.ui.JokeActivity;
import com.udacity.gradle.builditbigger.network.FetchJokeTask;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FetchJokeTask.AsyncResponse {
    private FetchJokeTask jokeTask;
    private Intent jokeDisplayIntemt;
    private ProgressBar progressBar;
    private Button jokeButton;

    public MainActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        jokeTask = new FetchJokeTask();
        jokeTask.setJokeResponce(this);
        jokeDisplayIntemt = new Intent(getActivity(), JokeActivity.class);
        jokeButton = (Button) root.findViewById(R.id.tell_joke_button);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                jokeTask.execute();
            }
        });
        return root;
    }


    @Override
    public void handleJoke(String joke) {
        progressBar.setVisibility(View.VISIBLE);
        if (joke == null) {
            joke = getString(R.string.server_down_error);
        }
        jokeDisplayIntemt.putExtra(JokeActivity.JOKE_KEY, joke);
        startActivity(jokeDisplayIntemt);
    }
}
