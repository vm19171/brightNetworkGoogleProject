package com.google;

import java.util.ArrayList;
import java.util.Comparator;

/** A class used to represent a Playlist */
class VideoPlaylist implements Comparable{

    private String title;
    private ArrayList<Video> videos;

    public VideoPlaylist(String name)
    {
        this.title = name;
        videos = new ArrayList<Video>();
    }


    public void add(Video v)
    {
        videos.add(v);
    }

    // clears videos list
    public void clear()
    {
        videos = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    @Override
    public int compareTo(Object o) {
        VideoPlaylist other = (VideoPlaylist)o;
        return this.getTitle().compareToIgnoreCase(other.getTitle());
    }


}

class compareTitles implements Comparator
{
    public int compare(Object p1, Object p2)
    {
        VideoPlaylist pl1 = (VideoPlaylist)p1;
        VideoPlaylist pl2 = (VideoPlaylist)p2;
        return pl1.getTitle().compareToIgnoreCase(pl2.getTitle());
    }
}
