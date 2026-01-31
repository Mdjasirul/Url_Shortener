package md.Url_shortner.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import md.Url_shortner.dto.ShortenRequest;
import md.Url_shortner.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService service;

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@Valid @RequestBody ShortenRequest request
    ) {
        String code = service.shortenUrl(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("http://localhost:8080/" + code);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String originalUrl = service.getOriginalUrl(code);
        System.out.println("ORIGINAL URL = " + originalUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", originalUrl)
                .build();
    }

}
