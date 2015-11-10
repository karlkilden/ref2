package com.kildeen.gv;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("javax.persistence.Entity")
    public class ComplexityProcessor extends AbstractProcessor {

        public ComplexityProcessor() {
            super();
        }

		@Override
		public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
			
			File f = new File("E:\\Data\\test.txt");
			System.out.println("ssad");
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

    }