package net.redone.conferenceservice.mappers;

import net.redone.conferenceservice.dto.ConferenceDto;
import net.redone.conferenceservice.dto.ReviewDto;
import net.redone.conferenceservice.entities.Conference;
import net.redone.conferenceservice.entities.Review;

import java.util.List;

public final class ConferenceMapper {
    private ConferenceMapper() {
    }

    public static ConferenceDto toDto(Conference conference) {
        if (conference == null) {
            return null;
        }
        List<ReviewDto> reviews = conference.getReviews() == null
                ? List.of()
                : conference.getReviews().stream()
                .map(ConferenceMapper::toDto)
                .toList();
        return ConferenceDto.builder()
                .id(conference.getId())
                .titre(conference.getTitre())
                .type(conference.getType())
                .date(conference.getDate())
                .duree(conference.getDuree())
                .nombreInscrits(conference.getNombreInscrits())
                .score(conference.getScore())
                .keynoteId(conference.getKeynoteId())
                .reviews(reviews)
                .build();
    }

    public static Conference toEntity(ConferenceDto dto) {
        if (dto == null) {
            return null;
        }
        Conference conference = Conference.builder()
                .id(dto.getId())
                .titre(dto.getTitre())
                .type(dto.getType())
                .date(dto.getDate())
                .duree(dto.getDuree())
                .nombreInscrits(dto.getNombreInscrits())
                .score(dto.getScore())
                .keynoteId(dto.getKeynoteId())
                .build();
        if (dto.getReviews() != null) {
            List<Review> reviews = dto.getReviews().stream()
                    .map(ConferenceMapper::toEntity)
                    .toList();
            reviews.forEach(review -> review.setConference(conference));
            conference.setReviews(reviews);
        }
        return conference;
    }

    public static ReviewDto toDto(Review review) {
        if (review == null) {
            return null;
        }
        return ReviewDto.builder()
                .id(review.getId())
                .date(review.getDate())
                .texte(review.getTexte())
                .stars(review.getStars())
                .build();
    }

    public static Review toEntity(ReviewDto dto) {
        if (dto == null) {
            return null;
        }
        return Review.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .texte(dto.getTexte())
                .stars(dto.getStars())
                .build();
    }
}
