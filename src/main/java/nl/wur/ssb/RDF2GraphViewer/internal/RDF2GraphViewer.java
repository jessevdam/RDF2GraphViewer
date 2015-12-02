package nl.wur.ssb.RDF2GraphViewer.internal;

import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.KeyStroke;

import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.osgi.framework.FrameworkUtil;

public class RDF2GraphViewer extends AbstractCySwingApp 
{
  private AutoSelectMenu autoSelectMenu;
  private boolean lastIncludeSubClasses = true, lastShowSimple = true, lastShowNonSimple = true;
	
	public RDF2GraphViewer(CySwingAppAdapter adapter)
	{
		super(adapter);
		System.out.println("plugin started3");
		MenuAction mainAction = new MenuAction(adapter,this,"Select.RDF2Graph.with-subclasses","all properties",true,true,true);
		mainAction.setAcceleratorKeyStroke(KeyStroke.getKeyStroke(new Character('e'),KeyEvent.SHIFT_DOWN_MASK));
		adapter.getCySwingApplication().addAction(mainAction);
		adapter.getCySwingApplication().addAction(new MenuAction(adapter,this,"Select.RDF2Graph.with-subclasses","simple properties",true,true,false));
		adapter.getCySwingApplication().addAction(new MenuAction(adapter,this,"Select.RDF2Graph.with-subclasses","non-simple properties",true,false,true));
		adapter.getCySwingApplication().addAction(new MenuAction(adapter,this,"Select.RDF2Graph.direct","all properties",false,true,true));
		adapter.getCySwingApplication().addAction(new MenuAction(adapter,this,"Select.RDF2Graph.direct","simple properties",false,true,false));
		adapter.getCySwingApplication().addAction(new MenuAction(adapter,this,"Select.RDF2Graph.direct","non-simple properties",false,false,true));
	  this.autoSelectMenu = new AutoSelectMenu(adapter);
	  adapter.getCySwingApplication().addAction(this.autoSelectMenu);
	}
	
	public void doAutoSelect()
	{
		if(this.autoSelectMenu.getAutoSelectOn())
		  this.selectShowPropos(lastIncludeSubClasses, lastShowSimple, lastShowNonSimple);
	}
	
  public void selectShowPropos(boolean includeSubClasses,boolean showSimple,boolean showNonSimple)
  {
  	this.lastIncludeSubClasses = includeSubClasses;
  	this.lastShowSimple = showSimple;
  	this.lastShowNonSimple = showNonSimple;
  	
    final CyApplicationManager manager = adapter.getCyApplicationManager();
    final CyNetworkView networkView = manager.getCurrentNetworkView();
    final CyNetwork network = networkView.getModel();
    
    for (CyNode node : CyTableUtil.getNodesInState(network,"selected",true)) 
    {
    	this.deSelectAllEdges(network);
    	selectAllOutEdges(network,node,includeSubClasses,showSimple,showNonSimple,null);
     }
    networkView.updateView();
  }
  
  private void selectAllOutEdges(CyNetwork network,CyNode node,boolean includeSubClasses,boolean showSimple,boolean showNonSimple,HashSet<CyNode> breakLoopList)
  {
  	if(breakLoopList == null)
  		breakLoopList = new HashSet<CyNode>();
  	breakLoopList.add(node);
  	for(CyEdge edge : network.getAdjacentEdgeIterable(node,CyEdge.Type.ANY))
  	{        			
  		if(edge.getSource() == node)
  		{
     		if(!getEdgeProp(network,edge,"interaction",String.class).equals("subClassOf"))
     		{
     			boolean isSimple = getEdgeProp(network,edge,"is_simple",Boolean.class);
     			if((isSimple && showSimple) || (!isSimple && showNonSimple))
  			    this.setEdgeSelected(network,edge);
     		}
     		else if(includeSubClasses && !breakLoopList.contains(edge.getTarget()))
     		{
     			this.selectAllOutEdges(network,edge.getTarget(),true,showSimple,showNonSimple,breakLoopList);
     		}       			
  		}
  	}
  }
  
  private <T> T getEdgeProp(CyNetwork network,CyEdge edge,String columnName, Class<?extends T> type)
  {
  	return network.getDefaultEdgeTable().getRow(edge.getSUID()).get(columnName,type);
  }
  
	public void setNodeSelected(CyNetwork net, CyNode node)
	{
		net.getDefaultNodeTable().getRow(node.getSUID()).set(CyNetwork.SELECTED,true);
	}
	
	public void deSelectAllEdges(CyNetwork net)
	{
		for(CyRow row : net.getDefaultEdgeTable().getAllRows())
		{
			row.set(CyNetwork.SELECTED,false);
		}
	}
	
	public void setEdgeSelected(CyNetwork net, CyEdge edge)
	{
		net.getDefaultEdgeTable().getRow(edge.getSUID()).set(CyNetwork.SELECTED,true);
	}
}
