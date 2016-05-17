package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.udacity.gradle.builditbigger.network.FetchJokeTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */


public class JokeTest extends ApplicationTestCase<Application> implements FetchJokeTask.AsyncResponse {

    CountDownLatch signal;
    String joke;
    FetchJokeTask jokeTask = null;

    public JokeTest() {
        super(Application.class);
    }


    public void testJoke() {
        try {
            signal = new CountDownLatch(1);
            jokeTask = new FetchJokeTask();
            jokeTask.setJokeResponce(this);
            jokeTask.execute();
            signal.await(10, TimeUnit.SECONDS);
            assertNotNull("joke is null", joke);
            assertFalse("joke is empty", joke.isEmpty());
        } catch (Exception ex) {
            fail();
        }
    }

    @Override
    public void handleJoke(String joke) {
        this.joke = joke;
        signal.countDown();

    }

}