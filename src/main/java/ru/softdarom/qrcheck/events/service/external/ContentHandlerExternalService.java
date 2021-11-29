package ru.softdarom.qrcheck.events.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.config.FeignConfig;
import ru.softdarom.qrcheck.events.model.dto.response.FileResponse;

import java.util.Collection;

@FeignClient(name = "content-handler", url = "${outbound.feign.content-handler.host}", configuration = FeignConfig.class)
public interface ContentHandlerExternalService {

    @PostMapping(value = "/files/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileResponse> upload(
            @RequestHeader("X-Application-Version") String version,
            @RequestHeader("X-ApiKey-Authorization") String apiKey,
            @RequestPart("files") Collection<MultipartFile> files
    );

    @GetMapping("/files/{fileIds}")
    ResponseEntity<FileResponse> get(
            @RequestHeader("X-Application-Version") String version,
            @RequestHeader("X-ApiKey-Authorization") String apiKey,
            @PathVariable("fileIds") Collection<Long> imageIds
    );
}