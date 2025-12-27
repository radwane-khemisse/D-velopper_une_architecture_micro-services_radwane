package net.redone.conferenceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeynoteDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String fonction;
}
