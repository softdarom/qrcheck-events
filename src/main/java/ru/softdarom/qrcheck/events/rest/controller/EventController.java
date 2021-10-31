package ru.softdarom.qrcheck.events.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mobile Events", description = "Контроллер взаимодействия с events посредством frontend-to-backend")
@RestController(value = "innerEventController")
@RequestMapping("/mobile/events")
public class EventController {

}