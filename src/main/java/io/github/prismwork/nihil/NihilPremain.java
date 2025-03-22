package io.github.prismwork.nihil;

import io.github.prismwork.nihil.trans.LauncherSettingsPageTrans;
import io.github.prismwork.nihil.trans.MainPageTrans;
import nilloader.api.ClassTransformer;
import nilloader.api.NilLogger;

public class NihilPremain implements Runnable {
	public static final NilLogger LOGGER = NilLogger.get("Nihil");
	
	@Override
	public void run() {
		LOGGER.info("Initialized.");
		ClassTransformer.register(new MainPageTrans());
		ClassTransformer.register(new LauncherSettingsPageTrans());
	}
}
