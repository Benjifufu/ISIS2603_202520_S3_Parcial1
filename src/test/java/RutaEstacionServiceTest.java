import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.services.RutaEstacionService;
import co.edu.uniandes.dse.parcial1.services.RutaService;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(RutaEstacionService.class)
public class RutaEstacionServiceTest {
    
    @Autowired
    private RutaEstacionService rutaEstacionService;
    
    
    private PodamFactory factory = new PodamFactoryImpl();

    private RutaEntity ruta = new RutaEntity();

    private Collection<EstacionEntity> estacionCollection = new ArrayList<>();

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {

        clearData();
        insertData();

 
    }

    /**
     * Limpa las tablas que estan involucradas en la prueba
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MiembroEquipoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EventoEntity").executeUpdate();
    }


    /**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		ruta = factory.manufacturePojo(RutaEntity.class);
		entityManager.persist(ruta);

		for (int i = 0; i < 3; i++) {
			EstacionEntity entity = factory.manufacturePojo(EstacionEntity.class);
			entityManager.persist(entity);
			entity.getRutas().add(ruta);
			estacionCollection.add(entity);
			ruta.getEstaciones().add(entity);	
		}
	}

    @Test
    void testAddEstacionRuta() throws EntityNotFoundException, IllegalOperationException {

        RutaEntity nuevaRuta = factory.manufacturePojo(RutaEntity.class);
        entityManager.persist(nuevaRuta);
        EstacionEntity estacion = factory.manufacturePojo(EstacionEntity.class);
        entityManager.persist(estacion);

        rutaEstacionService.addEstacionRuta(estacion.getId(), nuevaRuta.getId());
        EstacionEntity ultimaEstacion = rutaEstacionService.getEstacion(estacion.getId(), nuevaRuta.getId());

        assertEquals(estacion.getId(), ultimaEstacion.getId());
		assertEquals(estacion.getName(), ultimaEstacion.getName());
		assertEquals(estacion.getCapacidad(), ultimaEstacion.getCapacidad());
    }

    @Test
	void testAddInvalidEstacion() {
		assertThrows(EntityNotFoundException.class, ()->{
			RutaEntity nuevaRuta = factory.manufacturePojo(RutaEntity.class);
			entityManager.persist(nuevaRuta);
			rutaEstacionService.addEstacionRuta(0L,nuevaRuta.getId());
		});

}
