package mx.com.vepormas.pruebas.app_mockito.repositorios;

import java.util.List;

import mx.com.vepormas.pruebas.app_mockito.Datos;

public class preguntasInterfazImpl implements preguntasInterfaz{

    @Override
    public List<String> findPreguntasExamenId(Long id) {
        System.out.println("metodo real fpei");
        return Datos.preguntas;
    }

    @Override
    public void guardarVarias(List<String> preguntas) {
        System.out.println("metodo real gv");
    }
    
}
