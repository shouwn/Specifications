package com.shouwn.specifications.processor;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.shouwn.specifications.annotation.Detail;
import com.squareup.javapoet.FieldSpec;
import lombok.Data;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class DetailAnnotatedVariable {

    private VariableElement annotatedVariableElement;
    private String simpleVariableName;
    private String annotationType;
    private Class type;

    public DetailAnnotatedVariable(VariableElement variableElement)
            throws IllegalArgumentException{
        this.annotatedVariableElement = variableElement;
        Detail annotation = variableElement.getAnnotation(Detail.class);
        this.annotationType = annotation.type();

        Preconditions.checkArgument(!Strings.isNullOrEmpty(annotationType));

        this.simpleVariableName = variableElement.getSimpleName().toString();

        try {
            this.type = Class.forName(variableElement.asType().toString());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public FieldSpec fieldSpec(){
        return FieldSpec.builder(type, simpleVariableName)
                .addModifiers(Modifier.PRIVATE)
                .build();
    }
}
