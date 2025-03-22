package io.github.prismwork.nihil.trans;

import io.github.prismwork.nihil.NihilPremain;
import javafx.scene.control.Label;
import nilloader.api.NilModList;
import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;
import org.jackhuang.hmcl.Metadata;

@Patch.Class("org.jackhuang.hmcl.ui.main.MainPage")
public class MainPageTrans extends MiniTransformer {
    @Patch.Method("<init>()V")
    public void patchInit(PatchContext ctx) {
        ctx.search(
                INVOKESPECIAL("javafx/scene/control/Label", "<init>", "(Ljava/lang/String;)V")
        ).jumpAfter();

        ctx.add(
                DUP(),
                ASTORE(3),
                ALOAD(3),
                INVOKESTATIC(
                        "io/github/prismwork/nihil/trans/MainPageTrans$Hooks",
                        "onAddingTitleLabel",
                        "(Ljavafx/scene/control/Label;)V"
                )
        );
    }

    public static class Hooks {
        public static void onAddingTitleLabel(Label target) {
            NihilPremain.LOGGER.info("Patching the title label");

            int modCount = NilModList.getAll().size();
            String modWord = modCount == 1 ? "mod" : "mods";
            target.setText(Metadata.FULL_TITLE + " (" + modCount + " " + modWord + " loaded)");
        }
    }
}
