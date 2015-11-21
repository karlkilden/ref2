package com.kildeen.gv;

@FunctionalInterface
public interface DecoratorFunction<E, D> {
	public D decorate(E entityToDecorate);
}