package com.uma.example.springuma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uma.example.springuma.model.Cuenta;
import com.uma.example.springuma.model.CuentaService;

@RestController
public class CuentaController {
    
    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/cuentas")
    public List<Cuenta> getCuentas(){
        return cuentaService.getAllCuentas();
    }

    @GetMapping("/cuenta_/{id}")
    public Cuenta getCuenta(@PathVariable("id") Long id) {
        return cuentaService.getCuenta(id);
    }

    @PostMapping(value = "/cuenta_",     consumes = {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<?> save(@RequestBody Cuenta cuenta) {
        try{
            cuentaService.addCuenta(cuenta);
            return ResponseEntity.ok().body("Una nueva cuenta se ha anyadido");
        }catch (Exception er){
            return ResponseEntity.internalServerError().body("La cuenta ya existe");
        }
	}

    @PostMapping(value = "/cuenta_",   consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE} )
	public ResponseEntity<?> saveHTML  (Cuenta cuenta) {
        if (cuentaService.addCuenta(cuenta)==null)
            return ResponseEntity.internalServerError().body("La cuenta ya existe");
        else{
            return ResponseEntity.ok().body("Una nueva cuenta se ha anyadido");
        }
	}

    @DeleteMapping("/cuenta_")
    public ResponseEntity<?> deleteCuenta(@RequestBody Cuenta cuenta){
        cuentaService.removeCuenta(cuenta);
        return ResponseEntity.ok().body("Cuenta eliminada con exito");
    }

    @DeleteMapping("/cuenta_/{id}")
    public ResponseEntity<?> deleteCuenta(@PathVariable("id") Long id){
        cuentaService.removeCuentaID(id);
        return ResponseEntity.ok().body("Cuenta eliminada con exito");
    }
}
