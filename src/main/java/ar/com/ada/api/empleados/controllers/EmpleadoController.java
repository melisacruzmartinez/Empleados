package ar.com.ada.api.empleados.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.models.request.InfoEmpleadaRequest;
import ar.com.ada.api.empleados.models.request.SueldoModRequest;
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

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleada(@PathVariable int id){
        Empleado  empleada = empleadoService.obtenerPorId(id);
        if(empleada == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(empleada);

    }
    //devuelve una lista de empleados
    @GetMapping("/empleados/categorias/{categoriaId}")
    public ResponseEntity<List<Empleado>> listaPorCategoriaId(@PathVariable int categoriaId){
        List<Empleado> listaEmpleadas = categoriaService.obtenerPorId(categoriaId).getEmpleados();
        return ResponseEntity.ok(listaEmpleadas);
    }
    
    //el sueldo es una forma de expresar que es diferente al put de empleadas id
    // esta es para actualizar los sueldos
    @PutMapping("/empleados/{id}/sueldos")
    public ResponseEntity<GenericResponse> actualizarSueldo(@PathVariable int id, @RequestBody SueldoModRequest sueldoRequest){
        Empleado empleada = empleadoService.obtenerPorId(id);
        if(empleada == null){
            return ResponseEntity.notFound().build();
        }
        empleada.setSueldo(sueldoRequest.sueldoNuevo);
        empleadoService.grabar(empleada);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleada.getEmpleadoId();
        gR.message = "Sueldo Actualizado con exito";
        return ResponseEntity.ok(gR);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<GenericResponse> bajaEmpleada(@PathVariable int id){
        Empleado empleada = empleadoService.obtenerPorId(id);
        if(empleada == null){
            return ResponseEntity.notFound().build();
    }
    empleada.setFechaBaja(new Date());
    empleada.setEstadoId(1);
    empleadoService.grabar(empleada);
    GenericResponse gR = new GenericResponse();
    gR.isOk = true;
    gR.message = " Empleada da de baja :( ";
    return ResponseEntity.ok(gR);
}

}