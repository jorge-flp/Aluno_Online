package com.App_Escola.Api.Model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "evento")
public class EventoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_evento;

    private LocalDate data;
    private String titulo;
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "id_calendario", nullable = false)
    private CalendarioModel calendarioLetivo;

    private CalendarioModel calendarioModel;

    public EventoModel() {
    }

    // Getters e Setters
    public Integer getId_evento() {
        return id_evento;
    }

    public void setId_evento(Integer id_evento) {
        this.id_evento = id_evento;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public CalendarioModel getCalendarioLetivo() {
        CalendarioModel calendarioModel = null;
        return calendarioModel;
    }

    public void setCalendarioLetivo(CalendarioModel calendarioLetivo) {
        this.calendarioModel = calendarioLetivo;
    }
}