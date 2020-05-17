package it.italiancoders.postws.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentRequest {

    @JsonProperty("owner")
    @NotNull
    private String owner;

    @JsonProperty("text")
    @Length(min = 1, max = 250)
    @Column(nullable = false, name = "text", length= 250)
    private String text;

}
