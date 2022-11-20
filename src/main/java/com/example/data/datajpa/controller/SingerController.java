package com.example.data.datajpa.controller;

import com.example.data.datajpa.annotation.Logging;
import com.example.data.datajpa.entity.Singer;
import com.example.data.datajpa.service.SingerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/singer")
public class SingerController {
    @Autowired
    private SingerServiceImpl singerService;

    @GetMapping
    @Logging
    public ResponseEntity<Iterable<?>> getAll() {
        Iterable<?> singers = singerService.findAll();
        return ResponseEntity.ok().body(singers);
    }

    @GetMapping("/albums")
    public ResponseEntity<Iterable<?>> getAllWithAlbums() {
        Iterable<?> singers = singerService.findAllWitAlbum();
        return ResponseEntity.ok().body(singers);
    }

    @GetMapping("/{id}")
    @Logging
    public ResponseEntity<?> getById(@PathVariable long id) {
        return ResponseEntity.ok().body(singerService.findById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<Iterable<?>> filter(@RequestBody Singer singer) {
        Iterable<?> body = singerService.findWithCriteria(singer.getFirstName(), singer.getLastName());
        return ResponseEntity.accepted().body(body);
    }

    @PutMapping
    public ResponseEntity<?> addOrUpdate(@RequestBody Singer singer) {
        return ResponseEntity.ok().body(singerService.addOrUpdate(singer));
    }
}
