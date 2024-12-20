package proj.devMarceloCimadon.MovieRater.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.devMarceloCimadon.MovieRater.Dto.Artist.CreateArtistDto;
import proj.devMarceloCimadon.MovieRater.Models.Artist;
import proj.devMarceloCimadon.MovieRater.Services.ArtistService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/artist")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody CreateArtistDto createArtistDto){
        var artistId = artistService.createArtist(createArtistDto);
        return ResponseEntity.created(URI.create("/v1/artist/" + artistId.toString())).build();
    }

    @GetMapping("/{artistName}")
    public ResponseEntity<Artist> getArtistByName(@PathVariable("artistName") String artistName){
        var artist = artistService.findArtistByName(artistName);
        return ResponseEntity.ok(artist.get());
    }

    @GetMapping("/artists")
    public ResponseEntity<List<Artist>> listArtists(){
        var artists = artistService.listArtists();
        return ResponseEntity.ok(artists);
    }
}