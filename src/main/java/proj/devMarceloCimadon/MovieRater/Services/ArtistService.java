package proj.devMarceloCimadon.MovieRater.Services;

import org.springframework.stereotype.Service;
import proj.devMarceloCimadon.MovieRater.Dto.Artist.CreateArtistDto;
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

    public List<Artist> listArtists(){
        return artistRepository.findAll();
    }

    public Optional<Artist> findArtistByName(String name){
        var artist = artistRepository.findArtistByName(name);
        if (artist.isEmpty()){
            throw new RecordNotFoundException("Name", name);
        }
        return artist;
    }
}