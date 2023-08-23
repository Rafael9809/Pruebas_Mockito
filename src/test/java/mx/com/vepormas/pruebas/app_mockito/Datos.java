package mx.com.vepormas.pruebas.app_mockito;

import java.util.Arrays;
import java.util.List;

import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;

public class Datos {
    public final static List<String> preguntas = List.of("¿Como te llamas?","Que materia es esta");
    public final static List<Examen> examenes = Arrays.asList(new Examen(1L,"Matematicas",preguntas), 
        new Examen(2L,"Historia",preguntas), 
        new Examen(3L,"Mecanica",preguntas));
}
