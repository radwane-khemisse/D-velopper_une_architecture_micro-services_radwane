package net.redone.keynoteservice.services;

import net.redone.keynoteservice.dto.KeynoteDto;

import java.util.List;

public interface KeynoteService {
    KeynoteDto create(KeynoteDto dto);

    KeynoteDto update(Long id, KeynoteDto dto);

    void delete(Long id);

    KeynoteDto getById(Long id);

    List<KeynoteDto> getAll();
}
