package com.example.caio.model;

import com.example.caio.domain.enums.EnumRoles;
import org.hibernate.annotations.SQLDelete;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@EqualsAndHashCode(callSuper = false)
@SQLDelete(sql = "DELETE FROM roles WHERE id = ?")
public class Role extends AbstractEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column
    private EnumRoles enumRoles;
}
