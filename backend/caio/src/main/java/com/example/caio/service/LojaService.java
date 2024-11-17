package com.example.caio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.caio.domain.dto.lojas.LojaDeleteDto;
import com.example.caio.domain.dto.lojas.LojaGetDto;
import com.example.caio.domain.dto.lojas.LojaPostDto;
import com.example.caio.model.Loja;
import com.example.caio.repository.ILojaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LojaService {

    @Autowired
    private ILojaRepository lojaRepository;


    public LojaGetDto cadastroLoja(LojaPostDto lojaPostDto){
        Loja loja = new Loja();
        
        loja.setCnpj(lojaPostDto.getCnpj());
        loja.setEmail(lojaPostDto.getEmail());
        loja.setNome(lojaPostDto.getNome());
        loja.setSenha(lojaPostDto.getSenha());


        lojaRepository.save(loja);

        return new LojaGetDto(
            loja.getId(),
            loja.getNome(),
            loja.getCnpj(),
            loja.getEmail(),
            loja.getSenha()
        );
    }
    
    public LojaDeleteDto deletarLoja(LojaDeleteDto lojaDeleteDto){

       Optional<Loja> lojaOptional = lojaRepository.findById(lojaDeleteDto.getId());

       if (lojaOptional.isPresent()) {
        Loja loja = lojaOptional.get();
        lojaRepository.delete(loja);
        return lojaDeleteDto;
       } else {
        throw new EntityNotFoundException("Loja com o CNPJ" + lojaDeleteDto.getCnpj() + "não foi encontrada");
       }
        
    }


}
