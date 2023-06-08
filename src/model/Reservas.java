package model;

import java.math.BigDecimal;
import java.time.LocalDate;


public class Reservas {

	private Long id;
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	private BigDecimal valor;
	private String formaPago;
	
	public Reservas(LocalDate fechaEntrada, LocalDate fechaSalida, BigDecimal valor, String formaPago) {
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida= fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}

	public Reservas(long id, LocalDate fechaEntrada, LocalDate fechaSalida, String valorStr, String formaPago) {
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida= fechaSalida;
		this.valor = BigDecimal.valueOf(Long.valueOf(valorStr));
		this.formaPago = formaPago;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public LocalDate getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDate fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDate getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(LocalDate fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	@Override
	public String toString() {
		return id + "," + fechaEntrada + "," + fechaSalida + "," + valor + "," + formaPago;
	}



	
	
	
}

