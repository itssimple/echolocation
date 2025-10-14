package se.itssimple.echolocation;
import se.itssimple.echolocation.util.Reference;
import net.minecraftforge.fml.common.Mod;

@Mod(Reference.MOD_ID)
public class ModForge {

	public ModForge() {
		ModCommon.init();
	}

}