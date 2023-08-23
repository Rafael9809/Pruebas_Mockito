package mx.com.vepormas.pruebas.app_mockito.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepository;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepositoryImpl;

public class ExamenServiceImplTest {
    @Test
    void testFindExamenPorNombre() {
        ExamenRepository  exrep = mock(ExamenRepository.class);
        ExamenService exser = new ExamenServiceImpl(exrep);


        List<String> lista = new ArrayList<>();
        String p1 = "¿Como te llamas?";
        lista.add(p1);
        List<Examen> datos = Arrays.asList(new Examen(1L,"Matematicas",lista), 
        new Examen(2L,"Historia",lista), 
        new Examen(3L,"Mecanica",lista));
        when(exrep.findAll()).thenReturn(datos);
        String ntest = "Matematicas";
        Examen examen = exser.findExamenPorNombre(ntest);
        assertNotNull(examen);
        assertEquals(1L,examen.id());
        assertEquals(ntest,examen.nombre());
    }
}
