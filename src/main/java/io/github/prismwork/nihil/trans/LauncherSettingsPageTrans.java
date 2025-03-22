package io.github.prismwork.nihil.trans;

import io.github.prismwork.nihil.NihilPremain;
import io.github.prismwork.nihil.ui.ModListPage;
import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;
import org.jackhuang.hmcl.ui.SVG;
import org.jackhuang.hmcl.ui.construct.AdvancedListBox;
import org.jackhuang.hmcl.ui.construct.TabControl;
import org.jackhuang.hmcl.ui.construct.TabHeader;

@Patch.Class("org.jackhuang.hmcl.ui.main.LauncherSettingsPage")
public class LauncherSettingsPageTrans extends MiniTransformer {
    @Patch.Method("<init>()V")
    public void patchInit(PatchContext ctx) {
        ctx.search(
                INVOKEVIRTUAL(
                        "org/jackhuang/hmcl/ui/construct/TabHeader",
                        "select",
                        "(Lorg/jackhuang/hmcl/ui/construct/TabControl$Tab;)V"
                )
        ).jumpBefore();

        ctx.add(
                ALOAD(0),
                GETFIELD(
                        "org/jackhuang/hmcl/ui/main/LauncherSettingsPage",
                        "tab",
                        "Lorg/jackhuang/hmcl/ui/construct/TabHeader;"
                ),
                INVOKESTATIC(
                        "io/github/prismwork/nihil/trans/LauncherSettingsPageTrans$Hooks",
                        "onAddingTabs",
                        "(Lorg/jackhuang/hmcl/ui/construct/TabHeader;)V"
                )
        );

        ctx.jumpToLastReturn();
        ctx.searchBackward(
                INVOKEVIRTUAL(
                        "org/jackhuang/hmcl/ui/construct/AdvancedListBox",
                        "addNavigationDrawerTab",
                        "(Lorg/jackhuang/hmcl/ui/construct/TabHeader;Lorg/jackhuang/hmcl/ui/construct/TabControl$Tab;Ljava/lang/String;Lorg/jackhuang/hmcl/ui/SVG;)Lorg/jackhuang/hmcl/ui/construct/AdvancedListBox;"
                )
        ).jumpAfter();

        ctx.add(
                ALOAD(0),
                GETFIELD(
                        "org/jackhuang/hmcl/ui/main/LauncherSettingsPage",
                        "tab",
                        "Lorg/jackhuang/hmcl/ui/construct/TabHeader;"
                ),
                INVOKESTATIC(
                        "io/github/prismwork/nihil/trans/LauncherSettingsPageTrans$Hooks",
                        "onInitSidebar",
                        "(Lorg/jackhuang/hmcl/ui/construct/AdvancedListBox;Lorg/jackhuang/hmcl/ui/construct/TabHeader;)Lorg/jackhuang/hmcl/ui/construct/AdvancedListBox;"
                )
        );
    }

    public static class Hooks {
        public static void onAddingTabs(TabHeader target) {
            NihilPremain.LOGGER.info("Patching the launcher settings tabs");

            ModListPage.TAB = new TabControl.Tab<>("nilModList");
            ModListPage.TAB.setNodeSupplier(ModListPage::new);
            target.getTabs().add(ModListPage.TAB);
        }

        public static AdvancedListBox onInitSidebar(AdvancedListBox sidebar, TabHeader tab) {
            NihilPremain.LOGGER.info("Patching the launcher settings sidebar");

            sidebar.addNavigationDrawerTab(tab, ModListPage.TAB, "Nilmods", SVG.MENU);
            return sidebar;
        }
    }
}
