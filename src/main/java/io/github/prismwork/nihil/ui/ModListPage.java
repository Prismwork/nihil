package io.github.prismwork.nihil.ui;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import nilloader.api.NilMetadata;
import nilloader.api.NilModList;
import org.jackhuang.hmcl.ui.FXUtils;
import org.jackhuang.hmcl.ui.construct.ComponentList;
import org.jackhuang.hmcl.ui.construct.TabControl;
import org.jackhuang.hmcl.ui.construct.TwoLineListItem;

import java.util.List;

public class ModListPage extends StackPane {
    public static TabControl.Tab<ModListPage> TAB;

    public ModListPage() {
        ComponentList modList = new ComponentList();
        List<NilMetadata> mods = NilModList.getAll();
        for (NilMetadata mod : mods) {
            TwoLineListItem modItem = new TwoLineListItem();
            modItem.setTitle(mod.name);
            String subtitleText = mod.description + "\n" +
                    "Mod ID: " + mod.id + " | " +
                    "v" + mod.version + " | " +
                    "by " + mod.authors;
            modItem.setSubtitle(subtitleText);
            modList.getContent().add(modItem);
        }
        VBox content = new VBox(16.0F);
        content.setPadding(new Insets(10.0F));
        content.getChildren().setAll(ComponentList.createComponentListTitle("Loaded Mods"), modList);
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        FXUtils.smoothScrolling(scrollPane);
        this.getChildren().setAll(scrollPane);
    }
}
