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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.network.FetchJokeTask;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FetchJokeTask.AsyncResponse {
    private FetchJokeTask jokeTask;
    private Intent jokeDisplayIntemt;
    private ProgressBar progressBar;
    private Button jokeButton;
    private InterstitialAd mInterstitialAd;
    private String mJoke;

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
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                jokeDisplayIntemt.putExtra(JokeActivity.JOKE_KEY, mJoke);
                startActivity(jokeDisplayIntemt);
            }
        });
        AdRequest bigAdRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(bigAdRequest);


        jokeButton = (Button) root.findViewById(R.id.tell_joke_button);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                jokeTask.execute();
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }


    @Override
    public void handleJoke(String joke) {
        progressBar.setVisibility(View.INVISIBLE);
        if (joke == null) {
            joke = getString(R.string.server_down_error);
        }
        mJoke = joke;
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            jokeDisplayIntemt.putExtra(JokeActivity.JOKE_KEY, joke);
            startActivity(jokeDisplayIntemt);
        }
    }
}
