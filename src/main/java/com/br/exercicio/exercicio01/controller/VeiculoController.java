package com.br.exercicio.exercicio01.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.exercicio.exercicio01.dto.VeiculoDTO;
import com.br.exercicio.exercicio01.model.Veiculo;

import com.br.exercicio.exercicio01.service.VeiculoService;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    // @Autowired
    // private VeiculoRepo repo;

    @Autowired // injecao de dependência
    private VeiculoService service;

    @GetMapping // 1
    public ResponseEntity<List<VeiculoDTO>> getAll() {
        List<VeiculoDTO> lista = service.getAll();

        if (lista == null || lista.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }
    /*
     * public ResponseEntity<List<Veiculo>> getAll() {
     * List<Veiculo> lista = (List<Veiculo>) repo.findAll();
     * 
     * if(lista == null || lista.size() == 0) {
     * return ResponseEntity.notFound().build();
     * }
     * return ResponseEntity.ok(lista);
     * }
     */

    @GetMapping("/{id}") // 2
    public ResponseEntity<VeiculoDTO> getById(@PathVariable Long id) {

        Veiculo veiculo = service.getById(id);
        VeiculoDTO veiculoDTO = new VeiculoDTO(veiculo);
        return ResponseEntity.ok(veiculoDTO);

        // return ResponseEntity.notFound().build()

        // Optional<Veiculo> veiculoOptional = repo.findById(id)
        // if (veiculo == null) {
        // }
        // Veiculo veiculoEncontrado = veiculoOptional.get();Vecu
    }

    @PostMapping
    public ResponseEntity<Veiculo> newVeiculo(@RequestBody Veiculo novoVeiculo) {
        Veiculo veiculoInserido = service.newVeiculo(novoVeiculo);

        if (veiculoInserido == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoInserido);
    }
    /*
     * public ResponseEntity<Veiculo> newVeiculo(@RequestBody Veiculo novoVeiculo) {
     * // ao criar um novo veículo, não pode ter a chave primária
     * if (novoVeiculo.getId() > 0) {
     * return ResponseEntity.badRequest().build();
     * }
     * Veiculo veiculoInserido = repo.save(novoVeiculo);
     * return ResponseEntity.status(HttpStatus.CREATED).body(veiculoInserido); //
     * cód http 201 = inserido com sucesso
     * }
     */

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> updateVeiculo(@PathVariable long id, @RequestBody Veiculo veiculo) {
        Veiculo veiculoAtualizado = service.updateVeiculo(id, veiculo);
        if (veiculoAtualizado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(veiculoAtualizado);
    }

    /*
     * public ResponseEntity<Veiculo> updateVeiculo(@PathVariable long
     * id, @RequestBody Veiculo veiculo) {
     * Optional<Veiculo> veiculoOptional = repo.findById(id);
     * 
     * if (veiculoOptional.isEmpty()) {
     * return ResponseEntity.notFound().build();
     * }
     * veiculo.setId(id);
     * Veiculo veiculoAtualizado = repo.save(veiculo);
     * return ResponseEntity.ok(veiculoAtualizado);
     * }
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Veiculo> deleteById(@PathVariable Long id) {
        boolean apagado = service.deleteVeiculo(id);
        if (apagado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    /*
     * public ResponseEntity<Veiculo> deleteById(@PathVariable Long id) {
     * Optional<Veiculo> veiculoOptional = repo.findById(id);
     * 
     * if (veiculoOptional.isEmpty()) {
     * return ResponseEntity.notFound().build();
     * }
     * 
     * repo.deleteById(id);
     * return ResponseEntity.noContent().build();
     * }
     */

     @GetMapping("/placa/{placa}")
     public ResponseEntity<VeiculoDTO> getByPlaca(@PathVariable String placa) {
 
         Veiculo veiculo = service.getByPlaca(placa);
         
         VeiculoDTO veiculoDTO = new VeiculoDTO(veiculo);
         return ResponseEntity.ok(veiculoDTO);
     }
 
     @GetMapping("/anoFabricacao/{anoFabricacao}")
     public ResponseEntity<List<Veiculo>> getGreaterThanAnoFabricacao(@PathVariable int anoFabricacao) {
         return ResponseEntity.ok(service.getGreaterThanAnoFabricacao(anoFabricacao));
     }

     @GetMapping("/marca")
     public ResponseEntity<List<Veiculo>> getAllOrderMarca(){
        return ResponseEntity.ok(service.getAllOrderMarca());
     }
    

    
    
    
    

    
    

    
    

}
