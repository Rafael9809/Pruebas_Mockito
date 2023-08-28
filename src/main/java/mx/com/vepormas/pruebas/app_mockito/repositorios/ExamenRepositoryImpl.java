package mx.com.vepormas.pruebas.app_mockito.repositorios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mx.com.vepormas.pruebas.app_mockito.Datos;
import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;

public class ExamenRepositoryImpl implements ExamenRepository{

    public List<Examen> findAll() {
        System.out.println("findall real");
        return Datos.examenes;
    }

    public Examen guardar(Examen examen){
        System.out.println("guardar real");
        return examen;
    }
}
