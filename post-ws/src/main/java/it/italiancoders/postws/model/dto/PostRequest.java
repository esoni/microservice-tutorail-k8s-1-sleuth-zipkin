package it.italiancoders.postws.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostRequest {


    @JsonProperty("owner")
    @NotNull
    private String owner;

    @JsonProperty("text")
    @Size(min = 1, max = 250)
    @NotNull
    private String text;

}
