package md.Url_shortner.service;

import lombok.RequiredArgsConstructor;
import md.Url_shortner.dto.ShortenRequest;
import md.Url_shortner.model.UrlMapping;
import md.Url_shortner.repository.UrlRepository;
import md.Url_shortner.util.Base62Util;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository repository;

    public String shortenUrl(ShortenRequest request) {

        UrlMapping url = new UrlMapping();
        url.setOriginalUrl(request.getLongUrl());
        url.setClickCount(0L);

        // expiry
        if (request.getExpiryMinutes() != null) {
            url.setExpiryAt(
                    LocalDateTime.now().plusMinutes(request.getExpiryMinutes())
            );
        }

        // custom alias
        if (request.getCustomAlias() != null && !request.getCustomAlias().isBlank()) {
            String alias = request.getCustomAlias().trim();

            if (repository.existsByShortCode(alias)) {
                throw new IllegalStateException("Alias already exists");
            }

            url.setShortCode(alias);
            repository.save(url);
            return alias;
        }

        // auto-generated short code
        UrlMapping saved = repository.save(url);
        String code = Base62Util.encode(saved.getId());

        saved.setShortCode(code);
        repository.save(saved);

        return code;
    }

    public String getOriginalUrl(String code) {

        UrlMapping url = repository.findByShortCode(code)
                .orElseThrow(() -> new IllegalArgumentException("URL not found"));

        // expiry check
        if (url.getExpiryAt() != null &&
                url.getExpiryAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Link expired");
        }

        // increment click count
        url.setClickCount(url.getClickCount() + 1);
        repository.save(url);

        return url.getOriginalUrl();
    }
}
