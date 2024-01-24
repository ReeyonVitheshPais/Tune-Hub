package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;




@Controller
public class PlaylistController {

	@Autowired
	SongService songservice;
	
	@Autowired
	PlaylistService playlistservice;
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		
		List<Song> songList = songservice.fetchAllSongs();
		model.addAttribute("songse",songList);
		return "createPlaylist";
	}
	
	
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		//updating playList table
		playlistservice.addPlayList(playlist);
		
		//updating song table
		List<Song> songz  = playlist.getSongs();
		for(Song s : songz)
		{
			s.getPlaylist().add(playlist);
			songservice.updateSong(s);
		}
		return "adminHome";
	}
	
	@GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model) {
		List<Playlist> allplaylists = playlistservice.fetchAllPlaylist();
		model.addAttribute("alltheplaylist",allplaylists);
		return "displayPlaylist";
	}
	
	
}
