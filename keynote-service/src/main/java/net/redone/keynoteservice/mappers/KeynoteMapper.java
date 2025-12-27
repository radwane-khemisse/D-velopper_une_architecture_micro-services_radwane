package net.redone.keynoteservice.mappers;

import net.redone.keynoteservice.dto.KeynoteDto;
import net.redone.keynoteservice.entities.Keynote;

public final class KeynoteMapper {
    private KeynoteMapper() {
    }

    public static KeynoteDto toDto(Keynote keynote) {
        if (keynote == null) {
            return null;
        }
        return KeynoteDto.builder()
                .id(keynote.getId())
                .nom(keynote.getNom())
                .prenom(keynote.getPrenom())
                .email(keynote.getEmail())
                .fonction(keynote.getFonction())
                .build();
    }

    public static Keynote toEntity(KeynoteDto dto) {
        if (dto == null) {
            return null;
        }
        return Keynote.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .fonction(dto.getFonction())
                .build();
    }
}
