package mx.com.vepormas.pruebas.app_mockito.modelos;

import java.util.List;

public record Examen(Long id, String nombre, List<String> preguntas)  {}
