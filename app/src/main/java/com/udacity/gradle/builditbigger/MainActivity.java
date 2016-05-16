package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jokedisplaylibrary.ui.JokeActivity;
import com.udacity.gradle.builditbigger.network.FetchJokeTask;


public class MainActivity extends AppCompatActivity implements FetchJokeTask.AsyncResponse {
    private FetchJokeTask jokeTask;
    private Intent jokeDisplayIntemt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jokeTask = new FetchJokeTask();
        jokeTask.setJokeResponce(this);
        jokeDisplayIntemt = new Intent(this, JokeActivity.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically h
        // andle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void launchJokeActivity(View view){
        jokeTask.execute();
    }

    @Override
    public void handleJoke(String joke) {
        jokeDisplayIntemt.putExtra(JokeActivity.JOKE_KEY, joke);
        startActivity(jokeDisplayIntemt);
    }
}
