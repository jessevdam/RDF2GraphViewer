package nl.wur.ssb.RDF2GraphViewer.internal;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;

public class NodeSelectionListener implements RowsSetListener
{
	private RDF2GraphViewer master;
	public boolean enabled = true;
	
	public NodeSelectionListener(RDF2GraphViewer master)
	{
		this.master = master;
	}
	
	public void handleEvent(RowsSetEvent e)
	{
		if(e.containsColumn(CyNetwork.SELECTED) && e.getSource().getTitle().endsWith("default node"))
		{
      master.doAutoSelect();
		}
	}
}
