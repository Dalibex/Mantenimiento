package com.uma.example.springuma.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.uma.example.springuma.model.Cuenta;
import com.uma.example.springuma.model.CuentaService;

@Controller
public class CuentaViewController {

	@Autowired
    private CuentaService cuentaService;
	
	/*---Devuelve el formulario de index de cuentas---*/
	@GetMapping("/")
	public String indexCuentaView() {
		return "indexC";
	}

	/*---Devuelve el formulario para anyadir una nueva cuenta con una cuenta vacia---*/
	@GetMapping("/addCuenta")
	public String addCuentaView(Model model) {
		model.addAttribute("cuenta", new Cuenta());
		return "addCuenta";
	}

	/*---Devuelve el formulario para listar las cuentas del sistema---*/
	@GetMapping("/listCuenta")
	public String listCuentaView(Model model) {
		model.addAttribute("cuentas", cuentaService.getAllCuentas());
		
		return "listCuenta";
	}

	/*---Anade una nueva cuenta al sistema y vuelve a la pantalla de listar cuentas---*/
	@PostMapping("/cuenta")
	public String saveCuenta(Cuenta cuenta, BindingResult result, Model model) {
		try {
			cuentaService.addCuenta(cuenta);
			model.addAttribute("create", true);
		} catch (Exception er) {
			model.addAttribute("create", false);
		}
		return listCuentaView(model);
	}

	/*---Devuelve el formulario para editar una Cuenta---*/
	@GetMapping("/editCuenta/{id}")
	public String editCuentaView(@PathVariable("id") Long id, Model model) {

		model.addAttribute("cuenta", cuentaService.getCuenta(id));
		return "updateCuenta";
	}

	/*---Actualiza una cuenta del sistema y vuelve a la pantalla de listas cuentas---*/
	@PutMapping("/cuenta")
	public String update(Cuenta cuenta, Model model) {
		try {
			cuentaService.updateCuenta(cuenta);
			model.addAttribute("update", true);
		} catch (Exception er) {
			er.printStackTrace();
			model.addAttribute("update", false);
		}
		return listCuentaView(model);
	}

	/*---Elimina una cuenta a partir de su ID y vuelve a la pantalla de listar cuentas---*/
	@DeleteMapping("/cuenta/{id}")
	public String deleteCuenta(@PathVariable("id") Long id, Model model) {
		try {
			cuentaService.removeCuentaID(id);
			model.addAttribute("delete", true);
		} catch (Exception er) {
			model.addAttribute("delete", false);
		}
		return listCuentaView(model);
	}
    
}
