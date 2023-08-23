package mx.com.vepormas.pruebas.app_mockito.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mx.com.vepormas.pruebas.app_mockito.Datos;
import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepository;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepositoryImpl;
import mx.com.vepormas.pruebas.app_mockito.repositorios.preguntasInterfaz;

public class ExamenServiceImplTest {
    private ExamenRepository exrep;
    private ExamenService exser; 
    private preguntasInterfaz pi;

    @BeforeEach
    void initTests(){
        exrep = mock(ExamenRepository.class);
        pi = mock(preguntasInterfaz.class);
        exser = new ExamenServiceImpl(exrep,pi);
    }

    @Test
    void testFindExamenPorNombre() {
        List<String> lista = new ArrayList<>();
        String p1 = "¿Como te llamas?";
        lista.add(p1);

        when(exrep.findAll()).thenReturn(Datos.examenes);

        String ntest = "Matematicas";
        Optional<Examen> examen = exser.findExamenPorNombre(ntest);
        assertNotNull(examen);
        assertEquals(1L,examen.orElseThrow().id());
        assertEquals(ntest,examen.orElseThrow().nombre());
    }

    @Test
    void preguntaExamen(){
        when(exrep.findAll()).thenReturn(Datos.examenes);
        when(pi.findPreguntasExamenId(anyLong())).thenReturn(Datos.preguntas); //match argument
        Examen examen = exser.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(2,examen.preguntas().size());
    }

    @Test
    void preguntaExamenVerify(){
        when(exrep.findAll()).thenReturn(Datos.examenes);
        when(pi.findPreguntasExamenId(anyLong())).thenReturn(Datos.preguntas); //match argument
        Examen examen = exser.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(2,examen.preguntas().size());
        verify(exrep).findAll();
        verify(pi).findPreguntasExamenId(anyLong());
    }
}
