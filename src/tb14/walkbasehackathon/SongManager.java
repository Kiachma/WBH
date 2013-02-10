package tb14.walkbasehackathon;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Environment;

public class SongManager {
	// SDCard Path
	String TAG = "SongManager";
	final String MEDIA_PATH = Environment.getExternalStorageDirectory().toString()+"/media";
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	// Constructor
	public SongManager(){

	}

	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList(String path){
	    File home = new File(path);

	    if (home.listFiles(new FileExtensionFilter()).length > 0) {
	        for (File file : home.listFiles(new FileExtensionFilter())) {
	        	
	        	//Execute recursively if folder found
	        	if(file.isDirectory()){
	        		getPlayList(file.getAbsolutePath());
	        	}
	        	else{
		            HashMap<String, String> song = new HashMap<String, String>();
		            String[] temp = file.getName().substring(0, (file.getName().length() - 4)).split("/");
		            song.put("songTitle", temp[temp.length-1]);
		            song.put("songPath", file.getPath());
		            
		            // Adding each song to SongList
		            songsList.add(song);
	        	}
	        }
	    }
	    // return songs list array
	    return songsList;
	}

	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
	    public boolean accept(File dir, String name) {
	    	//Assumes something is a directory if it does not have a fileextension
	        return (name.endsWith(".mp3") || name.endsWith(".MP3") || name.endsWith(".ogg") || name.split("\\.").length==1);
	    }
	}
}
