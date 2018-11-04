package com.shouwn.specifications.processor;

import com.google.auto.service.AutoService;
import com.shouwn.specifications.annotation.Specifications;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({
        "com.shouwn.specifications.annotation.Specifications",
        "com.shouwn.specifications.annotation.Detail"
})
@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor{
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    private List<SpecificationsAnnotatedClass> classList = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for(Element annotatedElement : roundEnv.getElementsAnnotatedWith(Specifications.class)){

            if(annotatedElement.getKind() != ElementKind.CLASS)
                return this.error(annotatedElement,
                        "Only classes can be annotated with @%s",
                        Specifications.class.getSimpleName());

            TypeElement typeElement = (TypeElement) annotatedElement;

            classList.add(new SpecificationsAnnotatedClass(typeElement));
        }

        classList.forEach(clazz -> {
            try {
                clazz.generateCode(this.filer);
            } catch (IOException ignored) {
            }
        });

        return true;
    }

    private boolean error(Element e, String msg, Object... args){
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e
        );

        return true;
    }
}
