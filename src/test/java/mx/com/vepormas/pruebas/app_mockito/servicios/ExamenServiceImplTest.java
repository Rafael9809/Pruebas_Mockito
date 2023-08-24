package mx.com.vepormas.pruebas.app_mockito.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.com.vepormas.pruebas.app_mockito.Datos;
import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepository;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepositoryImpl;
import mx.com.vepormas.pruebas.app_mockito.repositorios.preguntasInterfaz;

@ExtendWith(MockitoExtension.class)
public class ExamenServiceImplTest {
    @Mock
    private ExamenRepository exrep;
    @Mock
    private preguntasInterfaz pi;

    @InjectMocks
    private ExamenServiceImpl exser; 


    @BeforeEach
    void initTests(){
        MockitoAnnotations.openMocks(this);
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
        when(pi.findPreguntasExamenId(anyLong())).thenReturn(Datos.preguntas); //match argument o usar anyTipoVariable
        Examen examen = exser.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(2,examen.preguntas().size());
    }

    @Test
    void preguntaExamenVerify(){
        when(exrep.findAll()).thenReturn(Datos.examenes);
        when(pi.findPreguntasExamenId(anyLong())).thenReturn(Datos.preguntas); 
        Examen examen = exser.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(2,examen.preguntas().size());
        verify(exrep).findAll();
        verify(pi).findPreguntasExamenId(anyLong());
    }

    @Test
    void noExisteExamenVerify(){
        when(exrep.findAll()).thenReturn(Datos.examenes);
        when(pi.findPreguntasExamenId(anyLong())).thenReturn(Datos.preguntas); 
        Examen examen = exser.findExamenPorNombreConPreguntas("Matematicas");
        assertNotNull(examen);
        verify(exrep).findAll();
        verify(pi).findPreguntasExamenId(anyLong());
    }

    @Test
    void testGuardarExamen(){
        when(exrep.guardar(any(Examen.class))).thenReturn(Datos.examen);
        Examen examen = exser.guardar(Datos.examen);
        assertNotNull(examen.id());
        assertEquals(4L,examen.id());
        assertEquals("Electronica",examen.nombre());
        verify(exrep).guardar(any(Examen.class));
        verify(pi).guardarVarias(anyList());
    }

    @Test
    void manejoException(){
        when(exrep.findAll()).thenReturn(Datos.examenes_nulos);
        when(pi.findPreguntasExamenId(isNull())).thenThrow(IllegalArgumentException.class);
        Exception ex = assertThrows(IllegalArgumentException.class, ()->exser.findExamenPorNombreConPreguntas("Matematicas"));
    }
}
