package net.redone.conferenceservice.services;

import net.redone.conferenceservice.dto.ConferenceDto;
import net.redone.conferenceservice.dto.KeynoteDto;
import net.redone.conferenceservice.dto.ReviewDto;

import java.util.List;

public interface ConferenceService {
    ConferenceDto create(ConferenceDto dto);

    ConferenceDto update(Long id, ConferenceDto dto);

    void delete(Long id);

    ConferenceDto getById(Long id);

    List<ConferenceDto> getAll();

    ReviewDto addReview(Long conferenceId, ReviewDto reviewDto);

    List<ReviewDto> getReviews(Long conferenceId);

    KeynoteDto getKeynote(Long conferenceId);
}
