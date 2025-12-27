package net.redone.conferenceservice.services;

import net.redone.conferenceservice.clients.KeynoteClient;
import net.redone.conferenceservice.dto.ConferenceDto;
import net.redone.conferenceservice.dto.KeynoteDto;
import net.redone.conferenceservice.dto.ReviewDto;
import net.redone.conferenceservice.entities.Conference;
import net.redone.conferenceservice.entities.Review;
import net.redone.conferenceservice.exceptions.ConferenceNotFoundException;
import net.redone.conferenceservice.mappers.ConferenceMapper;
import net.redone.conferenceservice.repositories.ConferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final KeynoteClient keynoteClient;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, KeynoteClient keynoteClient) {
        this.conferenceRepository = conferenceRepository;
        this.keynoteClient = keynoteClient;
    }

    @Override
    public ConferenceDto create(ConferenceDto dto) {
        Conference conference = ConferenceMapper.toEntity(dto);
        conference.setId(null);
        return ConferenceMapper.toDto(conferenceRepository.save(conference));
    }

    @Override
    public ConferenceDto update(Long id, ConferenceDto dto) {
        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new ConferenceNotFoundException(id));
        conference.setTitre(dto.getTitre());
        conference.setType(dto.getType());
        conference.setDate(dto.getDate());
        conference.setDuree(dto.getDuree());
        conference.setNombreInscrits(dto.getNombreInscrits());
        conference.setScore(dto.getScore());
        conference.setKeynoteId(dto.getKeynoteId());
        return ConferenceMapper.toDto(conferenceRepository.save(conference));
    }

    @Override
    public void delete(Long id) {
        if (!conferenceRepository.existsById(id)) {
            throw new ConferenceNotFoundException(id);
        }
        conferenceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ConferenceDto getById(Long id) {
        return conferenceRepository.findById(id)
                .map(ConferenceMapper::toDto)
                .orElseThrow(() -> new ConferenceNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDto> getAll() {
        return conferenceRepository.findAll()
                .stream()
                .map(ConferenceMapper::toDto)
                .toList();
    }

    @Override
    public ReviewDto addReview(Long conferenceId, ReviewDto reviewDto) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException(conferenceId));
        Review review = ConferenceMapper.toEntity(reviewDto);
        review.setConference(conference);
        conference.getReviews().add(review);
        conferenceRepository.save(conference);
        return ConferenceMapper.toDto(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviews(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException(conferenceId));
        return conference.getReviews().stream()
                .map(ConferenceMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public KeynoteDto getKeynote(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException(conferenceId));
        if (conference.getKeynoteId() == null) {
            return null;
        }
        return keynoteClient.getKeynoteById(conference.getKeynoteId());
    }
}
