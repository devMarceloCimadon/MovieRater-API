package proj.devMarceloCimadon.MovieRater.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.devMarceloCimadon.MovieRater.Dto.Studio.CreateStudioDto;
import proj.devMarceloCimadon.MovieRater.Dto.Studio.ResponseStudioDto;
import proj.devMarceloCimadon.MovieRater.Models.Studio;
import proj.devMarceloCimadon.MovieRater.Services.StudioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/studio")
public class StudioController {
    private final StudioService studioService;

    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    @PostMapping
    public ResponseEntity<Studio> createStudio(@RequestBody CreateStudioDto createStudioDto){
        var studioId = studioService.createStudio(createStudioDto);
        return ResponseEntity.created(URI.create("/v1/studio/" + studioId)).build();
    }

    @GetMapping("/{studioName}")
    public ResponseEntity<ResponseStudioDto> getStudioByName(@PathVariable("studioName") String studioName){
        var studio = studioService.findStudioByName(studioName);
        var studioResponse = ResponseStudioDto.fromEntity(studio);
        return ResponseEntity.ok(studioResponse);
    }

    @GetMapping("/studios")
    public ResponseEntity<List<ResponseStudioDto>> listStudios(){
        var studios = studioService.listStudios();
        return ResponseEntity.ok(studios);
    }
}
