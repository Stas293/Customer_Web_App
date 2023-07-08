package com.projects.customer_web_app.http.rest;

import com.projects.customer_web_app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping(value = "/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getResortImage() {
        log.info("Get resort image");
        return userService.getUserImage()
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping("/image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateImage(@RequestParam("file") MultipartFile file) {
        userService.updateImage(file);
    }
}
