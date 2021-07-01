package com.google;

import com.sun.security.jgss.GSSUtil;
import javafx.util.Pair;
import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video currentVideo;
  private boolean isPaused;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    // prints each video in the library
    System.out.println("Here's a list of all available videos:");
    for (Video v : videoLibrary.getVideos()) {
      System.out.println(v);
    }
  }

  public void playVideo(String videoId) {
    Video newVideo = videoLibrary.getVideo(videoId);

    if (newVideo != null) { // if the video exists
      if (currentVideo != null) { // if a video is currently playing
        System.out.println("Stopping video: " + currentVideo.getTitle());
      }
      currentVideo = newVideo;
      System.out.println("Playing video: " + currentVideo.getTitle());
      isPaused = false;
    }
    else
    {
      System.out.println("Cannot play video: Video does not exist");
    }
  }

    public void stopVideo() {
      if (currentVideo != null) {
        System.out.println("Stopping video: " + currentVideo.getTitle());
        currentVideo = null;
      }
      else
      {
        System.out.println("Cannot stop video: No video is currently playing");
      }
    }

    public void playRandomVideo() {
        List library = videoLibrary.getVideos();
        if (!(library.isEmpty())) {
          int num = (int) (Math.random() * library.size());
          Video vid = (Video) library.get(num);
          playVideo(vid.getVideoId());
        }
        else
        {
          System.out.println("No videos available");
        }
    }

    public void pauseVideo() {
        if (currentVideo != null) // if a video is currently playing
        {
          if (!isPaused)  // if it is not paused
          {
            isPaused = true; // pause video
            System.out.println("Pausing video: " + currentVideo.getTitle());
          }
          else // if video is already paused
          {
            System.out.println("Video already paused: " + currentVideo.getTitle());
          }
        }
        else
        {
          System.out.println("Cannot pause video: No video is currently playing");
        }
    }

    public void continueVideo() {

      if (currentVideo != null) {
        if (isPaused) {  // if paused
          isPaused = false; // unpause
          System.out.println("Continuing video: " + currentVideo.getTitle());
        } else {
          System.out.println("Cannot continue video: Video is not paused");
        }
      }
      else
      {
        System.out.println("Cannot continue video: No video is currently playing");
      }
    }

    public void showPlaying() {
        if (currentVideo != null)
        {
          String output = "Currently playing: " + currentVideo.toString();
          if (isPaused) output += " - PAUSED";
          System.out.println(output);
        }
        else
        {
          System.out.println("No video is currently playing");
        }
    }



    /* PART 2 */

    private ArrayList<VideoPlaylist> playlists = new ArrayList<>();

    public void createPlaylist(String playlistName) {
     VideoPlaylist newPlaylist = new VideoPlaylist(playlistName);

     // if playlists is not empty, compare and see if one with the same name already exists.
     if(!(playlists.isEmpty())) {
       for (VideoPlaylist playlist : playlists) {
         if ((newPlaylist.getTitle().compareToIgnoreCase(playlist.getTitle())) == 0)  {
           System.out.println("Cannot create playlist: A playlist with the same name already exists");
           return;
         }
       }
     }

     playlists.add(newPlaylist);
     System.out.println("Successfully created new playlist: " + newPlaylist.getTitle());
    }

    public void addVideoToPlaylist(String playlistName, String videoId) {

        boolean found = false;
        VideoPlaylist currentPlaylist = new VideoPlaylist("temp");


        for (VideoPlaylist playlist : playlists)
        {
            if (playlist.getTitle().compareToIgnoreCase(playlistName) == 0) //if a playlist with that name exists
            {
                // System.out.println("Playlist found!");
                found = true;
                currentPlaylist = playlist;
                break;
            }
        }

        if (!found)  // if playlist does not exist
        {
            System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
        }
        else // if playlist exists
        {
            Video v = videoLibrary.getVideo(videoId);
            if (v == null) // if video does not exist
            {
                System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
            }
            else
            {
                if (currentPlaylist.getVideos().contains(v))
                {
                    System.out.println("Cannot add video to " + playlistName + ": Video already added");
                    return;
                }
                currentPlaylist.add(v);
                System.out.println("Added video to " + playlistName + ": " + v.getTitle());
            }
        }
    }

    public void showAllPlaylists() {

        if (playlists.isEmpty())
        {
            System.out.println("No playlists exist yet");
        }
        else
        {
            Collections.sort(playlists, new compareTitles());
            System.out.println("Showing all playlists:");
            for (VideoPlaylist p : playlists) {
                System.out.println(p.getTitle());
            }
        }
    }

    public void showPlaylist(String playlistName) {

        boolean found = false;
        VideoPlaylist currentPlaylist = new VideoPlaylist("temp");

        for (VideoPlaylist playlist : playlists)
        {
            if (playlist.getTitle().compareToIgnoreCase(playlistName) == 0) //if a playlist with that name exists
            {
                found = true;
                currentPlaylist = playlist;
                break;
            }
        }

        if (!found)  // if playlist does not exist
        {
            System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
        }
        else // if playlist does exist
        {
            System.out.println("Showing playlist: " + playlistName);
            if (currentPlaylist.getVideos().isEmpty()) // if no videos in playlist
            {
                System.out.println("No videos here yet");
            }
            else
            {
                for (Video v : currentPlaylist.getVideos()) {
                    System.out.println(v);
                }
            }
        }
    }

    public void removeFromPlaylist(String playlistName, String videoId) {
        boolean found = false;
        VideoPlaylist currentPlaylist = new VideoPlaylist("temp");

        for (VideoPlaylist playlist : playlists)
        {
            if (playlist.getTitle().compareToIgnoreCase(playlistName) == 0) //if a playlist with that name exists
            {
                found = true;
                currentPlaylist = playlist;
                break;
            }
        }

        if (!found)  // if playlist does not exist
        {
            System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
        }
        else // if playlist does exist
        {
            Video v = videoLibrary.getVideo(videoId);
            if (v == null) // if video does not exist
            {
                System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
            }
            else
            {
                if (!(currentPlaylist.getVideos().contains(v))) // if video not in list
                {
                    System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
                }
                else // if video is in list
                {
                    currentPlaylist.getVideos().remove(v);
                    System.out.println("Removed video from " + playlistName + ": " + v.getTitle());
                }
            }
        }
    }

    public void clearPlaylist(String playlistName) {
        boolean found = false;
        VideoPlaylist currentPlaylist = new VideoPlaylist("temp");

        for (VideoPlaylist playlist : playlists)
        {
            if (playlist.getTitle().compareToIgnoreCase(playlistName) == 0) //if a playlist with that name exists
            {
                found = true;
                currentPlaylist = playlist;
                break;
            }
        }

        if (!found)  // if playlist does not exist
        {
            System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
        }
        else
        {
            currentPlaylist.clear();
            System.out.println("Successfully removed all videos from " + playlistName);
        }
    }

    public void deletePlaylist(String playlistName) {
        boolean found = false;
        VideoPlaylist currentPlaylist = new VideoPlaylist("temp");

        for (VideoPlaylist playlist : playlists)
        {
            if (playlist.getTitle().compareToIgnoreCase(playlistName) == 0) //if a playlist with that name exists
            {
                found = true;
                currentPlaylist = playlist;
                break;
            }
        }

        if (!found)  // if playlist does not exist
        {
            System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
        }
        else
        {
            playlists.remove(currentPlaylist);
            System.out.println("Deleted playlist: " + playlistName);
        }
    }


    /* PART 3 */


    public void searchVideos(String searchTerm) {
        int resultNum = 0;
        Map<Integer, Video> searchResults = new TreeMap<>(); // map of results

        for(Video v : videoLibrary.getVideos())
        {
            if (v.getTitle().toLowerCase().contains((searchTerm.toLowerCase()))) // if video found using search criteria
            {
                resultNum++;
                searchResults.put(resultNum, v); // add to map of search results
            }
        }



        if (searchResults.isEmpty()) // if no results found
        {
            System.out.println("No search results for " + searchTerm);
        }
        else
        {
            System.out.println("Here are the results for " + searchTerm + ":");
            for (Map.Entry<Integer, Video> entry: searchResults.entrySet())
            {
                System.out.println(entry.getKey() + ")" + " " + entry.getValue());
            }
            System.out.println("Would you like to play any of the above? If yes, specify the number of the video. ");
            System.out.println("If your answer is not a valid number, we will assume it's a no.");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            int intInput = 0;

            try {
                intInput = Integer.parseInt(input);

                for (Map.Entry<Integer, Video> entry: searchResults.entrySet())
                {
                    if (entry.getKey() == intInput)
                    {
                        playVideo(entry.getValue().getVideoId());
                    }
                }
            }
            catch (NumberFormatException n)
            {
                return;
            }

        }
    }

    public void searchVideosWithTag(String videoTag) {
        System.out.println("searchVideosWithTag needs implementation");
    }

    public void flagVideo(String videoId) {
        System.out.println("flagVideo needs implementation");
    }

    public void flagVideo(String videoId, String reason) {
        System.out.println("flagVideo needs implementation");
    }

    public void allowVideo(String videoId) {
        System.out.println("allowVideo needs implementation");
    }
}