package ar.com.ada.api.empleados.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.empleados.entities.Empleado;
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{
    //al crear este metodo no nesecitamos usar el optional
    Empleado findById(int id);
}