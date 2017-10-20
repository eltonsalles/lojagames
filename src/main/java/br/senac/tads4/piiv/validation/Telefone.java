package br.senac.tads4.piiv.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3,4})\\-?(\\d{4}))$")
public @interface Telefone {

	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Telefone inválido";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
