package mx.com.vepormas.pruebas.app_mockito.repositorios;

import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;
import java.util.List;

public interface ExamenRepository{
    List<Examen> findAll();
    Examen guardar(Examen examen);
}