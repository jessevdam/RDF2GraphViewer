package nl.wur.ssb.RDF2GraphViewer.internal;

import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;

public class NetworkListener implements NetworkAddedListener
{	
	public void handleEvent(NetworkAddedEvent e)
	{
		/*System.out.println("network added event2: " + e);
		if(e.getNetwork().getDefaultNodeTable().getTitle().startsWith("RDF2Graph.txt"))
		{
			System.out.println("RDF2Graph network loaded");
		}*/
	}	
}
