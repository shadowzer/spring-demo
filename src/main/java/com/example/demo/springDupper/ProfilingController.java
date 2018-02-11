package com.example.demo.springDupper;

/**
 * Created by cod_s on 10.02.2018.
 */
public class ProfilingController implements ProfilingControllerMBean {
	private boolean enabled = true;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
