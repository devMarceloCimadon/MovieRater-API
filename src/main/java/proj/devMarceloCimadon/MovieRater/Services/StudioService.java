package proj.devMarceloCimadon.MovieRater.Services;

import org.springframework.stereotype.Service;
import proj.devMarceloCimadon.MovieRater.Dto.Studio.CreateStudioDto;
import proj.devMarceloCimadon.MovieRater.Dto.Studio.ResponseStudioDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.Studio;
import proj.devMarceloCimadon.MovieRater.Repositories.StudioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudioService {
    private final StudioRepository studioRepository;

    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    public Long createStudio(CreateStudioDto createStudioDto){
        if (createStudioDto.name() == null){
            throw new RecordNotCreatedException();
        }
        var studio = new Studio();
        studio.setName(createStudioDto.name());

        var savedStudio = studioRepository.save(studio);
        return savedStudio.getStudioId();
    }

    public List<ResponseStudioDto> listStudios(){
        var studios = studioRepository.findAll();
        return studios.stream().map(ResponseStudioDto :: fromEntity).toList();
    }

    public Studio findStudioByName(String name){
        return studioRepository.findStudioByName(name).orElseThrow(() -> new RecordNotFoundException("Studio name", name));
    }
}
