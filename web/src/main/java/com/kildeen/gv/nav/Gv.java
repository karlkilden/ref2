package com.kildeen.gv.nav;

import static org.apache.deltaspike.jsf.api.config.view.View.NavigationMode.REDIRECT;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.View;

@View(navigation = REDIRECT)
public interface Gv {
	public interface P extends Gv {

		public class Poll implements ViewConfig {
		}

		public class Polls implements ViewConfig {
		}

	}

}
