package ar.com.ada.api.empleados.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.models.request.InfoEmpleadaRequest;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.*;


@RestController
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleado(@RequestBody InfoEmpleadaRequest info){
        Empleado empleada = new Empleado();
        empleada.setNombre(info.nombre);
        empleada.setEdad(info.edad);
        empleada.setSueldo(info.sueldo);
        empleada.setFechaAlta(new Date());
        Categoria categoria = categoriaService.obtenerPorId(info.categoriaId);
        empleada.setCategoria(categoria);
        empleada.setEstadoId(1);

        
        empleadoService.crearEmpleado(empleada);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleada.getEmpleadoId();
        gR.message = "Empleado creado con exito";
        return ResponseEntity.ok(gR);
    }

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>>listaEmpleadas(){
        return ResponseEntity.ok(empleadoService.obtenerEmpleados());
    }

}
