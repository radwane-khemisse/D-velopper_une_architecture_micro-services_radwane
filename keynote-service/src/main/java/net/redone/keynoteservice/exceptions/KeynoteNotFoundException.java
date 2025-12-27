package net.redone.keynoteservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KeynoteNotFoundException extends RuntimeException {
    public KeynoteNotFoundException(Long id) {
        super("Keynote not found with id=" + id);
    }
}
