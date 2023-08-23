package mx.com.vepormas.pruebas.app_mockito.servicios;

import java.util.Optional;

import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepository;

public class ExamenServiceImpl implements ExamenService{
    private ExamenRepository exRep;
    
    public ExamenServiceImpl(ExamenRepository exRep){
        this.exRep=exRep;
    }

    public Examen findExamenPorNombre(String nombre){
        Optional<Examen> exOp = exRep.findAll().stream().filter(x->x.nombre().contains(nombre)).findFirst();        
        Examen examen = null;
        if (exOp.isPresent()){
            examen=exOp.orElseThrow(null);
        }
        return examen;
    }
}
