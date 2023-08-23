package mx.com.vepormas.pruebas.app_mockito.servicios;

import java.util.List;
import java.util.Optional;

import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepository;
import mx.com.vepormas.pruebas.app_mockito.repositorios.preguntasInterfaz;

public class ExamenServiceImpl implements ExamenService{
    private ExamenRepository exRep;
    private preguntasInterfaz pi;
    
    public ExamenServiceImpl(ExamenRepository exRep, preguntasInterfaz pi){
        this.exRep=exRep;
        this.pi=pi;
    }

    public Optional<Examen> findExamenPorNombre(String nombre){
        return exRep.findAll().stream().filter(x->x.nombre().contains(nombre)).findFirst();               
    }

    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {
        List<String> preguntas=null;
        Optional<Examen> exOp = findExamenPorNombre(nombre);
        if(exOp.isPresent()){
            preguntas = pi.findPreguntasExamenId(exOp.orElseThrow().id());
        }
        return preguntas!=null?  exOp.orElseThrow() : null;
    }
}
