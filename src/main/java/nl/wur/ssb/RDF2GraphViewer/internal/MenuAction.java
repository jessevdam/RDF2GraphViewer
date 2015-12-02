package nl.wur.ssb.RDF2GraphViewer.internal;

import java.awt.event.ActionEvent;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.swing.AbstractCyAction;

public class MenuAction extends AbstractCyAction
{
	private static final long serialVersionUID = 4728624762049707677L;
	private RDF2GraphViewer master;
	private boolean includeSubClasses, showSimple, showNonSimple;
	
	public MenuAction(CyAppAdapter adapter, RDF2GraphViewer master,String menu,String name,boolean includeSubClasses,boolean showSimple,boolean showNonSimple)
	{
		super(name,adapter.getCyApplicationManager(),"network",adapter.getCyNetworkViewManager());
		this.master = master;
		this.includeSubClasses = includeSubClasses;
		this.showSimple = showSimple;
		this.showNonSimple = showNonSimple;
		setPreferredMenu(menu);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		this.master.selectShowPropos(this.includeSubClasses,this.showSimple,this.showNonSimple);
	}	
}
