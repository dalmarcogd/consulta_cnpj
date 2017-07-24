package consulta.cnpj.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import consulta.cnpj.controller.ConsultaCNPJController;
import consulta.cnpj.model.PessoaJuridica;

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
	private JTextArea taResultado;
	
	private ConsultaCNPJController controller;
	
	/**
	 * @return controller
	 */
	public ConsultaCNPJController getController() {
		if (controller == null) {
			controller = new ConsultaCNPJController();
		}
		return controller;
	}
	
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
		tfCNPJ.setPreferredSize(new Dimension(600, 20));
		addComponent(tfCNPJ, 0, 1, 3, 1, GridBagConstraints.WEST);
		addListenerCNPJ();
		
		JLabel lbNomeFantasia = new JLabel("Resultado:");
		addComponent(lbNomeFantasia, 1, 0, 1, 1, GridBagConstraints.EAST);
		taResultado = new JTextArea();
		taResultado.setPreferredSize(new Dimension(600, 250));
		addComponent(taResultado, 1, 1, 3, 1, GridBagConstraints.WEST);
		
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
	
	
	private void addListenerCNPJ() {
		tfCNPJ.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent event) {
				String cnpj = tfCNPJ.getText();
				try {
					PessoaJuridica pessoa = getController().consultaSiteReceita(cnpj);
					
					if (pessoa != null) {
						taResultado.setText(pessoa.toString());
						pnCadastro.repaint();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// Nada a fazer.
			}
		});
	}
}
