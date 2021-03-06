package com.udacity.gradle.builditbigger.network;

import android.os.AsyncTask;

import com.example.semwa.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by semwa on 15-05-2016.
 */
public class FetchJokeTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private AsyncResponse jokeResponce = null;

    public void setJokeResponce(AsyncResponse jokeResponce) {
        this.jokeResponce = jokeResponce;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setRootUrl("http://localhost:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }


        try {
            String joke = myApiService.sendJoke().execute().getData();
            return joke == null ? "bazinga" : joke;
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String result) {
        jokeResponce.handleJoke(result);
    }


    public interface AsyncResponse {
        void
        handleJoke(String output);
    }
}
