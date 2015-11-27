package nl.wur.ssb.RDF2GraphViewer;

import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;

public class RDF2GraphViewer extends AbstractCySwingApp 
{
	public RDF2GraphViewer(CySwingAppAdapter adapter)
	{
		super(adapter);
		System.out.println("plugin started2");
		adapter.getCySwingApplication().addAction(new MenuAction(adapter));
	}
}
