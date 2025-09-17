package co.edu.uniandes.dse.parcial1.services;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstacionRutaService {

    @Autowired
    private EstacionRepository estacionRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Transactional
    public EstacionEntity addEstacionRuta(Long estacionId, Long rutaId) throws EntityNotFoundException {
        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);
        if (rutaEntity.isEmpty()) { 
			throw new EntityNotFoundException("Ruta no encontrada");
		}
        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
		if (estacionEntity.isEmpty()) {
			throw new EntityNotFoundException("Estacion no encontrado");
		}

        if (rutaEntity.get().getEstaciones().contains(estacionEntity.get())){
			throw new EntityNotFoundException("Ruta ya contiene estacion encontrado");
		        }
        else
            rutaEntity.get().getEstaciones().add(estacionEntity.get());
	
		return estacionEntity.get();

    }

    @Transactional
	public void removeEstacionRuta(Long rutaId, Long estacionId) throws EntityNotFoundException {

		Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
		Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);

		if (estacionEntity.isEmpty()) {
			throw new EntityNotFoundException("Estacion no encontrada");
		}
		if (rutaEntity.isEmpty()) {
			throw new EntityNotFoundException("Ruta no encontrada");
	    }
        
        if (!rutaEntity.get().getEstaciones().contains(estacionEntity.get())){
			throw new EntityNotFoundException("Ruta no contiene estacion encontrado");}
        else
		    rutaEntity.get().getEstaciones().remove(rutaEntity.get());

    }
}
    
