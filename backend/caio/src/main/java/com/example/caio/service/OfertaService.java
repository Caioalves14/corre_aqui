package com.example.caio.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.caio.domain.dto.ofertas.OfertaDeleteDto;
import com.example.caio.domain.dto.ofertas.OfertaGetDto;
import com.example.caio.domain.dto.ofertas.OfertaPostDto;
import com.example.caio.model.Oferta;
import com.example.caio.repository.IOfertaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OfertaService {

    @Autowired
    IOfertaRepository iOfertaRepository;

    public OfertaGetDto cadastrarOferta(OfertaPostDto ofertaPostDto){
        Oferta oferta = new Oferta();

        oferta.setId(ofertaPostDto.getId());
        oferta.setImagem(ofertaPostDto.getImagem());
        oferta.setNomeProduto(ofertaPostDto.getNomeProduto());
        oferta.setPreco(ofertaPostDto.getPreco());

        iOfertaRepository.save(oferta);

        return new OfertaGetDto(
            oferta.getId(),
            oferta.getNomeProduto(),
            oferta.getImagem(),
            oferta.getPreco()
        );
    }

    public OfertaDeleteDto deletarOferta(OfertaDeleteDto ofertaDeleteDto){
        Optional<Oferta> ofertaOptinal = iOfertaRepository.findById(ofertaDeleteDto.getId());

        if (ofertaOptinal.isPresent()) {
            Oferta oferta = ofertaOptinal.get();
            iOfertaRepository.delete(oferta);
            return ofertaDeleteDto;
        }else{
            throw new EntityNotFoundException("Loja com o ID" + ofertaDeleteDto.getId() + "não foi encontrada");
        }
    }

    public List<OfertaGetDto> getAllofertas() {
        List<Oferta> ofertas = iOfertaRepository.findAll();
        System.out.println("Ofertas encontradas: " + ofertas);
    
        return ofertas.stream()
                      .map(oferta -> new OfertaGetDto(
                          oferta.getId(),
                          oferta.getNomeProduto(),
                          oferta.getImagem(),
                          oferta.getPreco()
                      ))
                      .collect(Collectors.toList());
    }
    

}
