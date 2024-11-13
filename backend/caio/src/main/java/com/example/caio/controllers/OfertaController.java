package com.example.caio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.caio.domain.dto.ofertas.OfertaDeleteDto;
import com.example.caio.domain.dto.ofertas.OfertaGetDto;
import com.example.caio.domain.dto.ofertas.OfertaPostDto;
import com.example.caio.service.OfertaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/api/oferta")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    @PostMapping(path = "/criar")
    public ResponseEntity<OfertaGetDto> criarOferta(@RequestBody OfertaPostDto ofertaPostDto) {
        OfertaGetDto criarOferta = ofertaService.cadastrarOferta(ofertaPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criarOferta);
    }

    @DeleteMapping(path = "/deletar")
    public ResponseEntity<Void> deletarOferta(@RequestBody OfertaDeleteDto ofertaDeleteDto){
        ofertaService.deletarOferta(ofertaDeleteDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
