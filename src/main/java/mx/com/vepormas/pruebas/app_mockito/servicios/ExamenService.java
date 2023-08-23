package mx.com.vepormas.pruebas.app_mockito.servicios;

import java.util.Optional;

import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;

public interface ExamenService {
    Optional<Examen> findExamenPorNombre(String Nombre);

    Examen findExamenPorNombreConPreguntas(String nombre);
}
