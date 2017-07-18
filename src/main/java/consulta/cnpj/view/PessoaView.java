package consulta.cnpj.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author André Felipe Bürger (andre.burger@publica.inf.br)
 *
 */
public class PessoaView {

	private GridBagLayout layout;
    private GridBagConstraints constraints;
    public final int horizontal = GridBagConstraints.HORIZONTAL;
    public final int both = GridBagConstraints.BOTH;
	
	private JFrame frCadastro;
	private JPanel pnCadastro;
	private JTextField tfCNPJ;
	private JTextField tfNomeFantasia;
	private JTextField tfRazaoSocial;
	private JTextField tfRua;
	
	/**
	 * 
	 */
	public PessoaView() {
		createComponents();
	}
	
	private void createComponents() {
		frCadastro = new JFrame("Consulta CNPJ");
		pnCadastro = new JPanel();
		layout = new GridBagLayout();
		pnCadastro.setLayout(layout);
		
		constraints = new GridBagConstraints();
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.insets = new Insets(5,5,5,5);
		
		
		JLabel lbCNPJ = new JLabel("CNPJ:");
		addComponent(lbCNPJ, 0, 0, 1, 1, GridBagConstraints.EAST);
		tfCNPJ = new JTextField();
		tfCNPJ.setPreferredSize(new Dimension(200, 20));
		addComponent(tfCNPJ, 0, 1, 3, 1, GridBagConstraints.WEST);
		
		JLabel lbNomeFantasia = new JLabel("Nome fantasia:");
		addComponent(lbNomeFantasia, 1, 0, 1, 1, GridBagConstraints.EAST);
		tfNomeFantasia = new JTextField();
		tfNomeFantasia.setPreferredSize(new Dimension(500, 20));
		addComponent(tfNomeFantasia, 1, 1, 3, 1, GridBagConstraints.WEST);
//		tfRazaoSocial = new JTextField();
//		addComponent(tfCNPJ, 2, 2, 3, 1, GridBagConstraints.HORIZONTAL);
//		tfRua = new JTextField();
//		addComponent(tfCNPJ, 3, 2, 3, 1, GridBagConstraints.HORIZONTAL);
		
		
		
		pnCadastro.add(tfCNPJ);
//		pnCadastro.add(tfNomeFantasia);
//		pnCadastro.add(tfRazaoSocial);
//		pnCadastro.add(tfRua);
		
		
		pnCadastro.setVisible(true);
		frCadastro.add(pnCadastro);
		frCadastro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frCadastro.pack();
		frCadastro.setVisible(true);
	}
	
	private void addComponent(Component comp, int row, int column, int width, int height, int fill){
		constraints.fill = fill;
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        layout.setConstraints(comp, constraints);
        pnCadastro.add(comp, constraints);
    }
	
	public static void main(String[] args) {
		new PessoaView();
	}
	
}
