package net.redone.conferenceservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConferenceNotFoundException extends RuntimeException {
    public ConferenceNotFoundException(Long id) {
        super("Conference not found with id=" + id);
    }
}
