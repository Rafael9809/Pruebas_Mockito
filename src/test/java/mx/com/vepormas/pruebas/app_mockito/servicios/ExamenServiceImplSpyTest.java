package mx.com.vepormas.pruebas.app_mockito.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import mx.com.vepormas.pruebas.app_mockito.Datos;
import mx.com.vepormas.pruebas.app_mockito.modelos.Examen;
import mx.com.vepormas.pruebas.app_mockito.repositorios.ExamenRepositoryImpl;
import mx.com.vepormas.pruebas.app_mockito.repositorios.preguntasInterfazImpl;

public class ExamenServiceImplSpyTest {

    @Spy
    private ExamenRepositoryImpl exrep;
    @Spy
    private preguntasInterfazImpl pi;

    @InjectMocks
    private ExamenServiceImpl exser; 

    @BeforeEach
    void initTests(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSpy(){
        doReturn(Datos.preguntas).when(pi).findPreguntasExamenId(anyLong());
        
        Examen examen = exser.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(1L,examen.id());
        assertEquals("Matematicas",examen.nombre());
        assertEquals(2, examen.preguntas().size());
        assertTrue(examen.preguntas().contains("¿Como te llamas?"));
    }
}
