package mx.com.vepormas.pruebas.app_mockito.repositorios;

import java.util.List;

public interface preguntasInterfaz {
    List<String> findPreguntasExamenId(Long id);
    void guardarVarias(List<String> preguntas);
}
