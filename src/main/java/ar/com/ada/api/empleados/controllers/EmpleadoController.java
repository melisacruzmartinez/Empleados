package ar.com.ada.api.empleados.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.EmpleadoService;

public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;

    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado){
    empleadoService.crearEmpleado(empleado);
    GenericResponse gR = new GenericResponse();
    gR.isOk = true;
    gR.id = empleado.getEmpleadoId();
    gR.message = "Empleado creado con exito";
    return ResponseEntity.ok(gR);
    
    }
    @PostMapping("/empleados")
    public ResponseEntity<List<Empleado>>listaEmpleadas(){
        return ResponseEntity.ok(empleadoService.obtenerEmpleados());
    }

}