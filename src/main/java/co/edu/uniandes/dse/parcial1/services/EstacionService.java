package co.edu.uniandes.dse.parcial1.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    @Transactional
    public EstacionEntity createEspectador(EstacionEntity estacion) throws IllegalOperationException{

        return estacionRepository.save(estacion);


    }
    
}
