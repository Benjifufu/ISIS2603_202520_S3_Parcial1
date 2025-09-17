package co.edu.uniandes.dse.parcial1.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class RutaService {
    
    @Autowired
    private RutaRepository rutaRepository;

    @Transactional
    public RutaEntity createRuta(RutaEntity ruta) throws IllegalOperationException{

        return rutaRepository.save(ruta);


    }

}
