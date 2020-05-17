package it.italiancoders.postws.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {

    @JsonProperty("id")
    @NotNull
    Long id;

    @JsonProperty("owner")
    @NotNull
    private String owner;

    @JsonProperty("text")
    @Size(min = 1, max = 250)
    @NotNull
    private String text;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
    private OffsetDateTime createdAt;
}
