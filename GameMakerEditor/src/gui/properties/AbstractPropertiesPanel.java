package gui.properties;

import gui.EditorFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;

public abstract class AbstractPropertiesPanel extends JPanel {

    private class PropertiesTableCellRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            JTextField editor = new JTextField();

            if (value != null) {
                editor.setText(value.toString());
            }

            if (column == 0) {
                editor.setBackground(getParent().getBackground());
            } else {
                editor.setBackground(Color.WHITE);
            }

            editor.setBorder(null);

            return editor;
        }

    }

    protected EditorFrame frame;

    protected String name;
    protected LinkedList<Property> properties;
    protected JTable table;

    public AbstractPropertiesPanel() {
        super();

        this.setLayout(new BorderLayout());
    }

    public void setProperties(LinkedList<Property> properties) {
        this.properties = properties;
    }

    public void setPanelName(String name) {
        this.name = name;
    }

    public String getPanelName() {
        return this.name;
    }

    public void setFrame(EditorFrame frame) {
        this.frame = frame;
    }

    public EditorFrame getFrame() {
        return this.frame;
    }

    public JTable getTable() {
        return this.table;
    }

    public abstract TableModelListener getTableModelListener();

    public void reload() {
        if (this instanceof DefaultPropertiesPanel) {
            return;
        }

        this.removeAll();

        JPanel marginWest = new JPanel();
        marginWest.setPreferredSize(new Dimension(12, 100));
        this.add(marginWest, BorderLayout.WEST);

        JPanel marginEast = new JPanel();
        marginEast.setPreferredSize(new Dimension(12, 100));
        this.add(marginEast, BorderLayout.EAST);

        JLabel nameLabel = new JLabel(this.getPanelName());
        nameLabel.setPreferredSize(new Dimension(200, 40));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(nameLabel, BorderLayout.NORTH);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Property");
        columnNames.add("Value");

        Vector<Vector<Object>> data = new Vector<>();
        for (Property p : properties) {
            Vector<Object> rowVector = new Vector<>();

            rowVector.add(p.getName());
            rowVector.add(p.getValue());

            data.add(rowVector);
        }

        table = new JTable(data, columnNames);
        table.setRowHeight(30);
        table.setBackground(this.getBackground());
        table.setDefaultRenderer(Object.class, new PropertiesTableCellRenderer());
        this.add(table, BorderLayout.CENTER);

        table.getModel().addTableModelListener(this.getTableModelListener());

        this.repaint();
        this.revalidate();
    }
}
