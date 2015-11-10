package com.kildeen.gv;

import java.io.File;
import java.io.IOException;
import java.nio.file.WatchEvent.Kind;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.persistence.Entity;
import javax.tools.JavaFileObject;

public class SillyProcessor extends AbstractProcessor { 

	private Filer filer;
	private Messager messager;
	private static int test = 1;

	@Override
	public void init(ProcessingEnvironment env) {
		filer = env.getFiler();
		messager = env.getMessager();
		env.getOptions();
		
		File f = new File("E:\\Data\\test.txt");
		System.out.println("ssad");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean process(Set<? extends TypeElement> elements, RoundEnvironment env) {
		
		for (Element element : env.getRootElements()) {
			Entity persistenceEntity = element.getAnnotation(Entity.class);
			
			if (persistenceEntity == null) {
				continue;
			}
			
			else {
				
				
			}
			
			
			
			messager.printMessage(javax.tools.Diagnostic.Kind.NOTE, "test:! " + element.getSimpleName().toString() + " " );
		}

		return true;
	}
}