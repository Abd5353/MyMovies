package com.example.mymovies;

public class FavoriteMovie {
    private int id;
    private String userName;
    private String movieName;
    private String movieYear;
    private String imdbID;

    public FavoriteMovie() {

    }

    public FavoriteMovie(int id, String userName, String movieName, String movieYear, String imdbID) {
        this.id = id;
        this.userName = userName;
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.imdbID = imdbID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPosterLink() {
        String url = "http://img.omdbapi.com/?i="+this.imdbID+"&apikey=1154cb05";
        return url;
    }
}
