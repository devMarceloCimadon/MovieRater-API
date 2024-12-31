package proj.devMarceloCimadon.MovieRater.Services;

import org.springframework.stereotype.Service;
import proj.devMarceloCimadon.MovieRater.Dto.Artist.CreateArtistDto;
import proj.devMarceloCimadon.MovieRater.Dto.Artist.ResponseArtistDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.Artist;
import proj.devMarceloCimadon.MovieRater.Repositories.ArtistRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Long createArtist(CreateArtistDto createArtistDto){
        if (createArtistDto.name() == null){
            throw new RecordNotCreatedException();
        }
        var artist = new Artist();
        artist.setName(createArtistDto.name());

        var savedArtist = artistRepository.save(artist);
        return savedArtist.getArtistId();
    }

    public List<ResponseArtistDto> listArtists(){
        var artists = artistRepository.findAll();
        return artists.stream().map(ResponseArtistDto :: fromEntity).toList();
    }

    public Artist findArtistByName(String name){
        return artistRepository.findArtistByName(name).orElseThrow(() -> new RecordNotFoundException("Artist name", name));
    }
}