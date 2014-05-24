package x.common.frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import x.common.exception.HadExsits;
import x.common.util.CheckHelper;

public class BaseFrame extends JFrame{
	
	private static final long serialVersionUID = 1998484191756119818L;
	private Map<String, Component> map;
	private Map<String, Object> data;
	protected BaseFrame parent;
	public BaseFrame(String title){
		this(title,null);
	}
	
	public BaseFrame(String title,BaseFrame parent){
		super(title);
		this.parent=parent;
		setResizable(false);
		map=new HashMap<String, Component>();
		data=new HashMap<>();
		create();
		addHandler();
	}
	
	public BaseFrame(){
		this("",null);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T select(String id,Class<T> type){
		return (T)map.get(id);
	}
	
	public Component add(Component comp,String id){
		super.add(comp);
		if(map.get(id)!=null){
			try {
				throw new HadExsits(id);
			} catch (HadExsits e) {
				e.printStackTrace();
			}
		}
		if(CheckHelper.isNotNullOrEmpty(id))
			map.put(id, comp);
		return comp;
	}
	
	protected void create(){
		
	}
	
	protected void addHandler(){
		
	}
	
	public void setCenter(int w,int h){
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screen.width-w>>1, (screen.height-h>>1)-50);
		setSize(w, h);
	}
	
	public void shave(){
		Point location=getLocation();
		for(int i=0;i<80;i++)
			setLocation(location.x, location.y+i);
		for(int i=80;i>=0;i--)
			setLocation(location.x, location.y+i);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key,Class<T> type) {
		return (T) data.get(key);
	}
	
	public void put(String key,Object obj){
		data.put(key, obj);
	}
}
