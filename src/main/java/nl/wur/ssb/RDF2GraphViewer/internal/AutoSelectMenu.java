package nl.wur.ssb.RDF2GraphViewer.internal;

import java.awt.event.ActionEvent;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.swing.AbstractCyAction;

public class AutoSelectMenu extends AbstractCyAction
{
	private static final long serialVersionUID = 7992677764795393603L;
	private boolean autoSelectOn = false;
	
	public AutoSelectMenu(CyAppAdapter adapter)
	{
		super("Enable auto select",adapter.getCyApplicationManager(),"network",adapter.getCyNetworkViewManager());
		setPreferredMenu("Select.RDF2Graph");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		autoSelectOn = !autoSelectOn;
		this.setName(autoSelectOn ? "Disable auto select" : "Enable auto select");		
	}	
	public boolean getAutoSelectOn()
	{
		return this.autoSelectOn;
	}
}

