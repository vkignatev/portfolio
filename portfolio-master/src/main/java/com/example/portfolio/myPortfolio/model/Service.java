package com.example.portfolio.myPortfolio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "services_seq", allocationSize = 1)
public class Service extends GenericModel {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
    @Column(name = "file")
    private String file;

}
