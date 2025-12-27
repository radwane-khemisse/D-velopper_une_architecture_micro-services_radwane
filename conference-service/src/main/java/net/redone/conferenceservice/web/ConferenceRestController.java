package net.redone.conferenceservice.web;

import net.redone.conferenceservice.dto.ConferenceDto;
import net.redone.conferenceservice.dto.KeynoteDto;
import net.redone.conferenceservice.dto.ReviewDto;
import net.redone.conferenceservice.services.ConferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/conferences")
public class ConferenceRestController {
    private final ConferenceService conferenceService;

    public ConferenceRestController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConferenceDto create(@RequestBody ConferenceDto dto) {
        return conferenceService.create(dto);
    }

    @GetMapping("/{id}")
    public ConferenceDto getById(@PathVariable Long id) {
        return conferenceService.getById(id);
    }

    @GetMapping
    public List<ConferenceDto> getAll() {
        return conferenceService.getAll();
    }

    @PutMapping("/{id}")
    public ConferenceDto update(@PathVariable Long id, @RequestBody ConferenceDto dto) {
        return conferenceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        conferenceService.delete(id);
    }

    @PostMapping("/{id}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto addReview(@PathVariable Long id, @RequestBody ReviewDto dto) {
        return conferenceService.addReview(id, dto);
    }

    @GetMapping("/{id}/reviews")
    public List<ReviewDto> getReviews(@PathVariable Long id) {
        return conferenceService.getReviews(id);
    }

    @GetMapping("/{id}/keynote")
    public KeynoteDto getKeynote(@PathVariable Long id) {
        return conferenceService.getKeynote(id);
    }
}
