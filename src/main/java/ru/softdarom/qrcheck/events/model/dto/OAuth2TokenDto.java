package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.base.TokenValidType;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OAuth2TokenDto {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("azp")
    private String azp;

    @JsonProperty("aud")
    private String aud;

    @NotNull
    @JsonProperty("sub")
    private String sub;

    @JsonProperty("scopes")
    private Set<String> scopes;

    @JsonProperty("email")
    private String email;

    @JsonProperty("valid")
    private TokenValidType valid;

}