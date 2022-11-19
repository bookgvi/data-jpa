package com.example.data.datajpa.controller;

import com.example.data.datajpa.service.SingerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/singer")
public class SingerController {
    private final SingerServiceImpl singerService;

    public SingerController(SingerServiceImpl singerService) {
        this.singerService = singerService;
    }

    @GetMapping
    public ResponseEntity<Iterable<?>> getAll() {
        Iterable<?> singers = singerService.findAll();
        return ResponseEntity.ok().body(singers);
    }

    @GetMapping("/albums")
    public ResponseEntity<Iterable<?>> getAllWithAlbums() {
        Iterable<?> singers = singerService.findAllWitAlbum();
        return ResponseEntity.ok().body(singers);
    }
}
