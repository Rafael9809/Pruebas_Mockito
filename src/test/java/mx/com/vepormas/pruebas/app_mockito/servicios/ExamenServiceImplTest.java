package mx.com.vepormas.pruebas.app_mockito.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import mx.com.vepormas.pruebas.app_mockito.Datos;
import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepository;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepositoryImpl;
import mx.com.vepormas.pruebas.app_mockito.repositorios.preguntasInterfaz;
import mx.com.vepormas.pruebas.app_mockito.repositorios.preguntasInterfazImpl;

@ExtendWith(MockitoExtension.class)
public class ExamenServiceImplTest {
    @Mock
    private ExamenRepositoryImpl exrep;
    @Mock
    private preguntasInterfazImpl pi;

    @InjectMocks
    private ExamenServiceImpl exser; 

    @Captor
    ArgumentCaptor<Long> captor;

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
        Examen examen = exser.findExamenPorNombreConPreguntas("Matematicas2");
        assertNull(examen);
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

    @Test 
    void testArgumentMatchers(){
        when(exrep.findAll()).thenReturn(Datos.examenes);
        when(pi.findPreguntasExamenId(anyLong())).thenReturn(Datos.preguntas); 
        exser.findExamenPorNombreConPreguntas("Matematicas");
        verify(exrep).findAll();
        verify(pi).findPreguntasExamenId(argThat(arg -> arg.equals(1L)));
    }


    @Test 
    void testArgumentMatchers2(){
        when(exrep.findAll()).thenReturn(Datos.examenes);
        when(pi.findPreguntasExamenId(anyLong())).thenReturn(Datos.preguntas); 
        exser.findExamenPorNombreConPreguntas("Matematicas");
        verify(exrep).findAll();
        verify(pi).findPreguntasExamenId(argThat(new MiArgMatchers()));
    }

    public static class MiArgMatchers implements ArgumentMatcher<Long>{

        private long argument;

        @Override
        public boolean matches(Long argument) {
            this.argument=argument;
            return argument != null && argument > 0 ;
        }

        @Override
        public String toString() {
            return"Mensaje personalizado de falla: " + argument + " debe ser entero y positivo";
        }
        
    }

    @Test
    void testArgumentCaptor(){
        when(exrep.findAll()).thenReturn(Datos.examenes);
        when(pi.findPreguntasExamenId(anyLong())).thenReturn(Datos.preguntas); 
        exser.findExamenPorNombreConPreguntas("Matematicas");
        //ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class); uso explicito sin anotacion @Captor
        verify(pi).findPreguntasExamenId(captor.capture());
        assertEquals(1L,captor.getValue());
    }

    @Test
    void testDoThrow(){
        Examen examen = Datos.examen;
        doThrow(IllegalArgumentException.class).when(pi).guardarVarias(anyList());
        assertThrows(IllegalArgumentException.class, ()->{
            exser.guardar(examen);
        });
    }

    @Test
    void testDoAnswer(){
        when(exrep.findAll()).thenReturn(Datos.examenes);
        doAnswer(invocation ->{
            Long id = invocation.getArgument(0);
            return id ==2L? Datos.preguntas: Collections.EMPTY_LIST;
        }).when(pi).findPreguntasExamenId(anyLong());
        Examen examen = exser.findExamenPorNombreConPreguntas("Matematicas");
        assertTrue(examen.preguntas().contains("¿Como te llamas?"));
        assertEquals(2, examen.preguntas().size());
        assertEquals(1L,examen.id());
        assertEquals("Matematicas",examen.nombre());
        verify(pi).findPreguntasExamenId(anyLong());
    }

    @Test
    void testDoAnswerGuardarExamen(){
        doAnswer(new Answer<Examen>(){
            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen = invocation.getArgument(0);
                return examen;
            }
        }).when(exrep).guardar(any(Examen.class));
        Examen examen = exser.guardar(Datos.examen);
        assertNotNull(examen.id());
        assertEquals(4L,examen.id());
        assertEquals("Electronica",examen.nombre());
        verify(exrep).guardar(any(Examen.class));
        verify(pi).guardarVarias(anyList());
    }

    @Test
    void testDoCallRealMethod(){
        when(exrep.findAll()).thenReturn(Datos.examenes);
        // when(pi.findPreguntasExamenId(anyLong())).thenReturn(Datos.preguntas); 
        doCallRealMethod().when(pi).findPreguntasExamenId(anyLong());
        Examen examen = exser.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(1L,examen.id());
        assertEquals("Matematicas",examen.nombre());
    }


}
