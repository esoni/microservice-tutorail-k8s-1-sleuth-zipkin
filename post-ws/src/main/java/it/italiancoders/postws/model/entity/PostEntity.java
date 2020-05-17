package it.italiancoders.postws.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonProperty("owner")
    @Column(nullable = false, name = "owner")
    private String owner;

    @JsonProperty("text")
    @Length(min = 1, max = 250)
    @Column(nullable = false, name = "text", length= 250)
    private String text;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
    @Column(nullable = false, name = "createdAt")
    private OffsetDateTime createdAt;
}
