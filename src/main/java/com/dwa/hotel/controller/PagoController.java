package com.dwa.hotel.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dwa.hotel.models.Pagos;

import com.dwa.hotel.models.Usuarios;
import com.dwa.hotel.servicios.ServicioPago;

import com.dwa.hotel.servicios.ServicioUsuario;

@Controller
public class PagoController {
	
	@Autowired
	ServicioPago servicioPago;
	
	@Autowired
	ServicioUsuario servicioUsuario;
	
	@GetMapping("/reservacion/registroPago")
	public String registroPago (Model modelo) {
		modelo.addAttribute("registroPago", new Pagos());
		return "/reservacion/registroPago";
	}
	
	@PostMapping("/reservacion/registroPago")
	public String registroPago(Model modelo, @ModelAttribute Pagos p, HttpSession sesion) {
		int iduser= Integer.parseInt(sesion.getAttribute("iduser").toString());
		Usuarios autor = servicioUsuario.buscar(iduser);
		if(autor== null || autor.getId()==0)
			modelo.addAttribute("error", servicioUsuario.getMensaje());
		else{
			p.setUsuarios(autor);
			if(!servicioPago.registroPago(p, sesion));
			modelo.addAttribute("error", servicioPago.getMensaje());
		}
			return "redirect:/reservacion/cuartos";
	}
}
	


