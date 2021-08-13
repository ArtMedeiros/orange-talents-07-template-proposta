package com.zupacademy.kleysson.proposta.config.validation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)
@ReportAsSingleViolation
@Constraint(validatedBy = { })
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFouCNPJ {
    String message() default "Documento inválido";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
