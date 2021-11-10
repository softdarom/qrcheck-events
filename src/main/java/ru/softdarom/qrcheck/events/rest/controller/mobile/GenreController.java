package ru.softdarom.qrcheck.events.rest.controller.mobile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiGetAllGenreTypes;
import ru.softdarom.qrcheck.events.model.base.GenreType;

import java.util.Collection;
import java.util.EnumSet;

@Tag(name = "Mobile Genres", description = "Контроллер взаимодействия с events для frontend-to-backend")
@RestController(value = "mobileGenreController")
@RequestMapping("/mobile/genres")
public class GenreController {

    @Operation(summary = "Получение всех жанров")
    @ApiGetAllGenreTypes
    @GetMapping("/types")
    public ResponseEntity<Collection<GenreType>> getAll(@RequestHeader(value = "X-Application-Version") String version) {
        return ResponseEntity.ok(EnumSet.allOf(GenreType.class));
    }
}