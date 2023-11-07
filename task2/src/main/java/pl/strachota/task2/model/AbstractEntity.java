package pl.strachota.task2.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@EqualsAndHashCode(of = "uuid")
public class AbstractEntity implements Serializable {

    private String uuid = UUID.randomUUID().toString();

    @Version
    private Long version;

}