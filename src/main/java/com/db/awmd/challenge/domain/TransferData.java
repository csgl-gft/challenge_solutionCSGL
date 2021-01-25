package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/******************/

@Data
public class TransferData {

	@NotNull
	@NotEmpty
	private final String toAccountId;

	@NotNull
	@Min(value = 0, message = "A transfer with a negative ammount can not be crated.")
	private final BigDecimal ammount;

	@JsonCreator
	public TransferData(@JsonProperty("toAccountId") String toAccountId, @JsonProperty("ammount") BigDecimal ammount) {
		this.toAccountId = toAccountId;
		this.ammount = ammount;
	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public String getToAccountId() {
		return toAccountId;
	}

}
