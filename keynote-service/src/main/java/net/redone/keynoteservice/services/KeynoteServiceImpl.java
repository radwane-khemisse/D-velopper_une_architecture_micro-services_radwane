package net.redone.keynoteservice.services;

import net.redone.keynoteservice.dto.KeynoteDto;
import net.redone.keynoteservice.entities.Keynote;
import net.redone.keynoteservice.exceptions.KeynoteNotFoundException;
import net.redone.keynoteservice.mappers.KeynoteMapper;
import net.redone.keynoteservice.repositories.KeynoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KeynoteServiceImpl implements KeynoteService {
    private final KeynoteRepository keynoteRepository;

    public KeynoteServiceImpl(KeynoteRepository keynoteRepository) {
        this.keynoteRepository = keynoteRepository;
    }

    @Override
    public KeynoteDto create(KeynoteDto dto) {
        Keynote keynote = KeynoteMapper.toEntity(dto);
        keynote.setId(null);
        return KeynoteMapper.toDto(keynoteRepository.save(keynote));
    }

    @Override
    public KeynoteDto update(Long id, KeynoteDto dto) {
        Keynote keynote = keynoteRepository.findById(id)
                .orElseThrow(() -> new KeynoteNotFoundException(id));
        keynote.setNom(dto.getNom());
        keynote.setPrenom(dto.getPrenom());
        keynote.setEmail(dto.getEmail());
        keynote.setFonction(dto.getFonction());
        return KeynoteMapper.toDto(keynoteRepository.save(keynote));
    }

    @Override
    public void delete(Long id) {
        if (!keynoteRepository.existsById(id)) {
            throw new KeynoteNotFoundException(id);
        }
        keynoteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public KeynoteDto getById(Long id) {
        return keynoteRepository.findById(id)
                .map(KeynoteMapper::toDto)
                .orElseThrow(() -> new KeynoteNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeynoteDto> getAll() {
        return keynoteRepository.findAll()
                .stream()
                .map(KeynoteMapper::toDto)
                .toList();
    }
}
