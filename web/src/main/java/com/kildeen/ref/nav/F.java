package com.kildeen.ref.nav;

import static org.apache.deltaspike.jsf.api.config.view.View.NavigationMode.REDIRECT;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.View;

@View(navigation = REDIRECT)
public interface F {

	public interface Fac extends F {
		public class Fact implements ViewConfig {
		}

		public class Facts implements ViewConfig {
		}

	}

	public interface Cat extends F {

		public class Category implements ViewConfig {
		}

		public class Categories implements ViewConfig {
		}

	}

}
