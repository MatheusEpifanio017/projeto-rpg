
package com.rpg.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpg.catalogo.CatalogoClasses;
import com.rpg.catalogo.CatalogoRacas;
import com.rpg.model.Classe;
import com.rpg.model.Raca;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {

    @GetMapping("/racas")
    public List<Raca> listarRacas() {
        return CatalogoRacas.listar();
    }

    @GetMapping("/classes")
    public List<Classe> listarClasses() {
        return CatalogoClasses.listar();
    }
}
    