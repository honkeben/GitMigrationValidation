package ssc.swarc.helper.vcs.migration.valdiator.ui.util;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class LocationInputVerifier extends InputVerifier{
	String regex = "^(\\/)(\\.*[a-z0-9]+\\/)*";
	@Override
    public boolean verify(JComponent input) {
		
        String text = ((JTextField) input).getText();
        return text.startsWith("/") || text.startsWith("e.g.") || text.isEmpty();
       
    }

}
