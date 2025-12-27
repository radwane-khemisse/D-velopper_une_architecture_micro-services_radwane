package net.redone.conferenceservice.clients;

import net.redone.conferenceservice.dto.KeynoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "keynote-service")
public interface KeynoteClient {
    @GetMapping("/api/keynotes/{id}")
    KeynoteDto getKeynoteById(@PathVariable("id") Long id);
}
