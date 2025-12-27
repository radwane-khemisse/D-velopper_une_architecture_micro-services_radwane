package net.redone.keynoteservice.web;

import net.redone.keynoteservice.dto.KeynoteDto;
import net.redone.keynoteservice.services.KeynoteService;
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
@RequestMapping("/api/keynotes")
public class KeynoteRestController {
    private final KeynoteService keynoteService;

    public KeynoteRestController(KeynoteService keynoteService) {
        this.keynoteService = keynoteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KeynoteDto create(@RequestBody KeynoteDto dto) {
        return keynoteService.create(dto);
    }

    @GetMapping("/{id}")
    public KeynoteDto getById(@PathVariable Long id) {
        return keynoteService.getById(id);
    }

    @GetMapping
    public List<KeynoteDto> getAll() {
        return keynoteService.getAll();
    }

    @PutMapping("/{id}")
    public KeynoteDto update(@PathVariable Long id, @RequestBody KeynoteDto dto) {
        return keynoteService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        keynoteService.delete(id);
    }
}
