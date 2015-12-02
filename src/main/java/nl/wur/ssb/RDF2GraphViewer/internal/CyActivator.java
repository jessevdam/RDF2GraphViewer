package nl.wur.ssb.RDF2GraphViewer.internal;

import java.util.Properties;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.model.events.RowsSetListener;
import org.cytoscape.service.util.AbstractCyActivator;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator
{
	public void start(BundleContext context) throws Exception
	{		
		CySwingAppAdapter adapter = getService(context,CySwingAppAdapter.class);
		RDF2GraphViewer mainApp = new RDF2GraphViewer(adapter);
  	registerService(context	,new NetworkListener(), NetworkAddedListener.class, new Properties());
		registerService(context	,new NodeSelectionListener(mainApp), RowsSetListener.class, new Properties());
	}

}
