package ru.softdarom.qrcheck.events.rest.controller.mobile;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiGetAllGenreTypes;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.model.dto.LocaleTypeDto;
import ru.softdarom.qrcheck.events.service.mobile.LocaleTypeService;

import java.util.Locale;
import java.util.Set;

@Tag(name = "Mobile Genres", description = "Контроллер взаимодействия с events для frontend-to-backend")
@RestController(value = "mobileGenreController")
@RequestMapping("/mobile/genres")
public class GenreController {

    private final LocaleTypeService<GenreType> genreService;

    @Autowired
    GenreController(LocaleTypeService<GenreType> genreService) {
        this.genreService = genreService;
    }

    @ApiGetAllGenreTypes
    @GetMapping("/types")
    public ResponseEntity<Set<LocaleTypeDto>> getAll(@RequestHeader(value = "X-Application-Version") String version,
                                                     Locale locale) {
        return ResponseEntity.ok(genreService.getLocaleTypes(locale));
    }
}