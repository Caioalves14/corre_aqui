package com.example.caio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.caio.domain.dto.lojas.LojaDeleteDto;
import com.example.caio.domain.dto.lojas.LojaGetDto;
import com.example.caio.domain.dto.lojas.LojaPostDto;
import com.example.caio.service.LojaService;
import org.springframework.web.bind.annotation.DeleteMapping;



@Controller
@RequestMapping("/api/loja")
public class LojaController {

    @Autowired
    private LojaService lojaService;

    @PostMapping(path = "/cadastro")
    public ResponseEntity<LojaGetDto> cadastrarLoja(@RequestBody LojaPostDto lojaPostDto){
        LojaGetDto cadatrarLoja = lojaService.cadastroLoja(lojaPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadatrarLoja);
    }
    
    @DeleteMapping(path = "/deletar")
    public ResponseEntity<Void> deletarLoja(@RequestBody LojaDeleteDto lojaDeleteDto) {
        lojaService.deletarLoja(lojaDeleteDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
