package co.edu.uniandes.dse.parcial1.entities;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity
public class RutaEntity extends BaseEntity {

    private String nombre;
    private String color;
    private String tipo;

    @PodamExclude
    @ManyToMany
    private Collection<EstacionEntity> estaciones = new ArrayList<>();

}
