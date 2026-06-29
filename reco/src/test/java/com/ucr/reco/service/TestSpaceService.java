package com.ucr.reco.service;

import com.ucr.reco.model.Reservation;
import com.ucr.reco.model.Space;
import com.ucr.reco.repository.SpaceJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)//Indica que la clase realizará verificaciones simuladas de pruebas unitarias.
public class TestSpaceService {

    @Mock//indica que se debe crear un Mock con el JpaRepository.
    private SpaceJpaRepository repositoryTest;

    @InjectMocks //inyecta dependencias necesarias para utilizar al Mock del repositorio como un repositorio simulado
    private SpaceService serviceTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
//indica que este metodo realizará una simulación de una verificación
    void getAll() {//retorna la lista de Sapaces que hay registrados(GETAll)
        Space spaceTest1 = (new Space(1, "Salón Kame House", "San Ramón", "Salón", 25000.0));//los 3 espacios instanciados para hacer la prueba
        Space spaceTest2 = (new Space(2, "El Baratie", "Palmares", "Cafetería", 15000.0));
        Space spaceTest3 = (new Space(3, "Luffy's Boat", "San José", "Restaurante y Salón", 40000.0));
        when(repositoryTest.findAll()).thenReturn(List.of(spaceTest1, spaceTest2, spaceTest3));//indica que cuando(when()), repositorio llame al metodo retornará(.thenReturn()) la lista de objetos que previamente se instanciaron.
        List<Space> listSpaceResult = repositoryTest.findAll();//crea la lista de objetos con lo que se indicó previamente
        assertNotNull(listSpaceResult);//verifica que no venga vacía
        assertEquals("Salón Kame House", listSpaceResult.get(0).getName());//assertEquals() verifica que los datos del string a verificar coincida
        assertEquals("El Baratie", listSpaceResult.get(1).getName());//con el objeto llamado con el metodo listSpaceResult.get(posición).getName()
        assertEquals("Luffy's Boat", listSpaceResult.get(2).getName());
        verify(repositoryTest, times(1)).findAll();//realiza la verificación y llamado del findAll() mediante el repository, 1 vez

    }

    @Test
    void getByNameSucces() {//retorna un objeto mediante su nombre(GETBY)
        Space spaceTest = new Space(2, "El Baratie", "San Ramón", "Salón", 25000.0);//objeto preestablecido
        when(repositoryTest.getByName("El Baratie")).thenReturn(spaceTest);//cuando llame al objeto con nombre "El Baratie" retornará el objeto predeterminado
        Space spaceResult = repositoryTest.getByName("El Baratie");//establece el objeto resultado
        assertNotNull(spaceResult);//verifica que no sea null
        assertEquals("El Baratie", spaceResult.getName());//verifica su coincidencia de atributos
        verify(repositoryTest, times(1)).getByName("El Baratie");//realiza la verificación total 1 vez
    }

    @Test
    void getByNameNoSucces() {//simula un caso en el que el nombre es null, por ende está malo
        Space spaceTest = new Space(2, null, "San Ramón", "Salón", 25000.0);//objeto predeterminado con nombre vacío
        when(repositoryTest.getByName("El Baratie")).thenReturn(null);//cuando verifica el objeto llamado debe retornar null
        Space spaceResult = repositoryTest.getByName("El Baratie");//guarda el objeto resultado
        assertNull(spaceResult);//verifica que venga null
        verify(repositoryTest, times(1)).getByName("El Baratie");//realiza la verificación total 1 vez
    }

    @Test
    void addSpaceSucces() {//simula un caso donde se añade un objeto al repositorio(POST)
        Space spaceTest = new Space(null, "Ramen de Ichiraku", "Alajuela", "Restaurante Japonés", 20000.0);// simula el dto
        Space establishedSpace = new Space(4, "Ramen de Ichiraku", "Alajuela", "Restaurante Japonés", 20000.0);//establece id faltante
        when(repositoryTest.getByName("Ramen de Ichiraku")).thenReturn(null);//verifica que no exista anteriormente
        when(repositoryTest.save(spaceTest)).thenReturn(establishedSpace);//retorna el objeto establecido cuando se llame el .save y se le ingresen los datos
        Space spaceResult = serviceTest.add(spaceTest);//guarda el objeto resultado
        assertNotNull(spaceResult);//verififca que no sea null
        verify(repositoryTest, times(1)).getByName("Ramen de Ichiraku");//verifica que no exista
        verify(repositoryTest, times(1)).save(spaceTest);//y luego verifica que sí lo guarde
    }

    @Test
    void addSpaceNoSuccesExists() {//simula un caso donde el objeto ya existía
        Space spaceTest = new Space(null, "Ramen de Ichiraku", "Alajuela", "Restaurante Japonés", 20000.0);//dto simulado
        when(repositoryTest.getByName("Ramen de Ichiraku")).thenReturn(spaceTest);//verifica que se encuentre el objeto y lo retorne
        Space spaceResult = serviceTest.add(spaceTest);//guarda el objeto
        assertNull(spaceResult);//verifica que no sea null
        verify(repositoryTest).getByName("Ramen de Ichiraku");//verifica que se encuentre el objeto
        verify(repositoryTest, never()).save(any());//realiza la verificación y hace que no guarde el objeto NUNCA si ya existía
    }

    @Test
    void addSpaceNoSuccesEmptyData() {//simula un caso donde hay datos vacíos
        Space spaceTest = new Space(null, null, "Alajuela", "Restaurante Japonés", 20000.0);//objeto simulado con datos faltantes
        when(repositoryTest.getByName(null)).thenReturn(null);//verifica que el nombre esté vacío y retorna null
        Space spaceResult = serviceTest.add(spaceTest);//registra el resultado
        assertNull(spaceResult);//verifica que sea null
        verify(repositoryTest).getByName(null);//lo busca por el nombre, pero null debido a la falta de datos
        verify(repositoryTest, never()).save(any());//verifica que si haya servido el null y no guarda nada, debidp a que no es un objeto válido
    }

    @Test
    void updateSucces() {//simula la actualización de un objeto(put)
        Space spaceTest = (new Space(3, "Luffy's Boat", "San José", "Restaurante y Salón", 40000.0));//simula el objeto que se desea actualizar
        Space newSpace = (new Space(null, "Luffy's Kingdom", "San José y Alajuela", "Restaurante", 70000.0));//simula el dto del objeto modificado
        Space newspaceTest = (new Space(3, "Luffy's Kingdom", "San José y Alajuela", "Restaurante", 70000.0));//simula que guardó el objeto ya modificado
        when(repositoryTest.findById(3)).thenReturn(Optional.of(spaceTest));//cuando se encuentre el objeto con id 3 retorna el original que coincide
        when(repositoryTest.save(spaceTest)).thenReturn(newspaceTest);//cuando llama al objeto nuevo retorna el objeto modificado
        Space spacerResult = serviceTest.update(3, newspaceTest);//registra el resultado
        assertNotNull(spacerResult);//verifica que no sea null
        assertEquals("Luffy's Kingdom", spacerResult.getName());//verifica que los atributos coincidan
        assertEquals("San José y Alajuela", spacerResult.getLocation());
        assertEquals("Restaurante", spacerResult.getType());
        assertEquals(70000.0, spacerResult.getPrice());
        verify(repositoryTest).findById(3);//llama al objeto simulado con id 3
        verify(repositoryTest).save(spaceTest);//guarda al objeto ya actualizado
    }

    @Test
    void updateNoSucces() {//simula la actualización de un objeto fallida
        Space newSpace = (new Space(null, "Luffy's Kingdom", "San José y Alajuela", "Restaurante", 70000.0));//simula el dto de los nuevos datos
        when(repositoryTest.findById(3)).thenReturn(Optional.empty()); //cuando llama al objeto de id 3 retorna empty()
        Space spaceResult = serviceTest.update(3, newSpace);//registra el resultado
        assertNull(spaceResult);//verifica que sea null
        verify(repositoryTest, times(1)).findById(3);//verifica al objeto id 3
        verify(repositoryTest, never()).save(any());//al no encontrarlo no retorna ni guarda nada
    }


    @Test
    void changePriceSucces() {//simula la actualización de un atributo del objeto(PATCH)
        Space spaceTest = (new Space(3, "Luffy's Boat", "San José", "Restaurante y Salón", 40000.0));
        Space newspaceTest = (new Space(3, "Luffy's Boat", "San José", "Restaurante y Salón", 70000.0));
        when(repositoryTest.findById(3)).thenReturn(Optional.of(spaceTest));
        when(repositoryTest.save(spaceTest)).thenReturn(newspaceTest);
        Space spacerResult = serviceTest.changePrice(3, 70000.0);
        assertNotNull(spacerResult);
        assertEquals(70000.0, spacerResult.getPrice());
        verify(repositoryTest).findById(3);
        verify(repositoryTest).save(spaceTest);

    }


    @Test
    void changePriceNoSuccesException() {//simula un update fallido que devuelve una excepción
        Space spaceTest = (new Space(2, "El Baratie", "Palmares", "Cafetería", 15000.0));//establece el objeto predefinido
        when(repositoryTest.findById(2)).thenReturn((Optional.of(spaceTest)));//cuando el repositorio busque por id retorna un Optional del objeto con los nuevos
        when(repositoryTest.save(spaceTest)).thenThrow(new RuntimeException("ERROR, el precio no pudo ser actualizado"));//arroja una excepción cuando se intenta actualizar el objeto

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {//verifica que sí ocurrió la excepción
            serviceTest.changePrice(2, 30000.0);//esperando que al intentar el cambio de atributo se lance dicha excepción
        });
        assertEquals("ERROR, el precio no pudo ser actualizado", exception.getMessage());//valida que la excepción
        verify(repositoryTest).findById(2);//verifica el llamado al objeto de id 2
        verify(repositoryTest).save(spaceTest);//verifica que el metodo haya servido y sí haya saltado la excepción
    }

    @Test
    void deleteSucces() {//simula un metodo delete(Delete)
        Space spaceTest = new Space(2, "El Baratie", "Palmares", "Cafetería", 15000.0);//establece el objeto predefinido
        when(repositoryTest.findById(2)).thenReturn(Optional.of(spaceTest));//cuando el repositorio busque por id retorna un Optional del objeto
        Space spaceResult = serviceTest.delete(2);//simula el llamado al metodo delete del service
        assertNotNull(spaceResult);//verifica que el objeto no sea null
        assertEquals("El Baratie", spaceResult.getName());//valida los atributos necesarios
        verify(repositoryTest).findById(2);//llama al objeto
        verify(repositoryTest).deleteById(2);//realiza la verificación del delete
    }

    @Test
    void deleteNoSucces() {//simula un delete fallido
        when(repositoryTest.findById(3)).thenReturn(Optional.empty());//cuando llama al objeto que no existe retorna un empty
        Space spaceResult = serviceTest.delete(3);//establece el resultado
        assertNull(spaceResult);//valida que el resultado sea null
        verify(repositoryTest).findById(3);//llama al objeto
        verify(repositoryTest, never()).deleteById(anyInt());//realiza la verificación del delete donde no retorna ningún id ni objeto
    }


}
