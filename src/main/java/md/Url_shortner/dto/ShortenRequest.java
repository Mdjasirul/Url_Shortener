package md.Url_shortner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortenRequest {
    @NotBlank
    @org.hibernate.validator.constraints.URL
    private String longUrl;
    private String customAlias;
    private Long expiryMinutes;
}
