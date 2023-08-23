package mx.com.vepormas.pruebas.app_mockito.servicios;

import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;

public interface ExamenService {
    Examen findExamenPorNombre(String Nombre);
}
