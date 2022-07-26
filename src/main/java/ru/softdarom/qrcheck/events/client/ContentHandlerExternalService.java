package ru.softdarom.qrcheck.events.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.config.FeignConfig;
import ru.softdarom.qrcheck.events.model.dto.response.FileResponse;

import java.util.Collection;
import java.util.UUID;

@FeignClient(name = "content-handler", url = "${outbound.feign.content-handler.host}", configuration = FeignConfig.class)
public interface ContentHandlerExternalService {

    @PostMapping(value = "/files/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileResponse> upload(
            @RequestHeader("X-ApiKey-Authorization") UUID apiKey,
            @RequestPart("files") Collection<MultipartFile> files
    );

    @GetMapping("/files/{fileIds}")
    ResponseEntity<FileResponse> get(
            @RequestHeader("X-ApiKey-Authorization") UUID apiKey,
            @PathVariable("fileIds") Collection<Long> imageIds
    );
}