package com.example.semwa.myapplication.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class JokeBean {

    private String funnyJoke;

    public String getData() {
        return funnyJoke;
    }

    public void setData(String data) {
        funnyJoke = data;
    }
}