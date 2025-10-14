package se.itssimple.echolocation;

import se.itssimple.echolocation.data.Constants;
import se.itssimple.echolocation.util.Reference;

public class ModCommon {
	public static void init() {
		Constants.LOG.info("Loading {} (ID: {}), version {}", Reference.NAME, Reference.MOD_ID, Reference.VERSION);
		load();
	}

	private static void load() {

	}
}