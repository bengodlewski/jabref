package net.sf.jabref.gui.actions.bibsonomy;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;

import net.sf.jabref.bibsonomy.BibSonomyProperties;
import net.sf.jabref.gui.IconTheme;
import net.sf.jabref.gui.JabRefFrame;
import net.sf.jabref.gui.bibsonomy.GroupingComboBoxItem;
import net.sf.jabref.gui.worker.bibsonomy.RefreshTagListWorker;
import net.sf.jabref.gui.worker.bibsonomy.UpdateVisibilityWorker;
import net.sf.jabref.logic.l10n.Localization;

import org.bibsonomy.common.enums.GroupingEntity;

/**
 * Runs the {@link RefreshTagListWorker} to refresh the tag cloud
 */
public class RefreshTagListAction extends AbstractBibSonomyAction {

    private static List<GroupingComboBoxItem> defaultGroupings;

    private JEditorPane tagCloud;

    private JComboBox<? super GroupingComboBoxItem> groupingComboBox;

    public void actionPerformed(ActionEvent e) {
        // refresh the tag cloud
        RefreshTagListWorker worker = new RefreshTagListWorker(getJabRefFrame(), tagCloud, ((GroupingComboBoxItem) groupingComboBox.getSelectedItem()).getKey(), ((GroupingComboBoxItem) groupingComboBox.getSelectedItem()).getValue());
        performAsynchronously(worker);

        // update the "import posts from.." combo box
        UpdateVisibilityWorker visWorker = new UpdateVisibilityWorker(getJabRefFrame(), groupingComboBox, getDefaultGroupings());
        performAsynchronously(visWorker);
    }

    public RefreshTagListAction(JabRefFrame jabRefFrame, JEditorPane tagCloud, JComboBox<? super GroupingComboBoxItem> groupingComboBox) {
        super(jabRefFrame, Localization.lang("Refresh"), IconTheme.JabRefIcon.REFRESH.getIcon());
        this.tagCloud = tagCloud;

        this.groupingComboBox = groupingComboBox;
    }

    private static List<GroupingComboBoxItem> getDefaultGroupings() {
        if (defaultGroupings == null) {
            defaultGroupings = new ArrayList<>();
            defaultGroupings.add(new GroupingComboBoxItem(GroupingEntity.ALL, "all users"));
            defaultGroupings.add(new GroupingComboBoxItem(GroupingEntity.USER, BibSonomyProperties.getUsername()));
        }
        return defaultGroupings;
    }
}
