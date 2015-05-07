import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.DirectColorModel;
import java.util.Iterator;
import java.util.Vector;

class Dir extends Entry
{
	Vector<Entry> files;


	Dir(String n, Vector<Entry> fs, long s)
	{
		name = n;
		files = fs;
		size = s;

	}

	void print(String prefix)
	{
		System.out.println(prefix + name + ":"+"		"+age);
		for (int i = 0; i < files.size(); i++)
		{
			((Entry) files.elementAt(i)).print(prefix + name + "\\");
		}
		
		fullname = prefix + name + ":" + size;
	}
	
	String getName(int x, int y){
		Iterator<Entry> it = files.iterator();
		String ret = "";
		while(ret.isEmpty() && it.hasNext()){
			ret = it.next().getName(x, y);
		}
		return ret;
	}

	public void draw(Graphics g, long minage, long maxage){
		Iterator<Entry> it = files.iterator();
		while (it.hasNext()){
			it.next().draw(g, minage,maxage);
		}
		g.setColor(Color.GREEN);
		g.drawRect(Math.round(x), Math.round(y), Math.round(w), Math.round(h));
	//	g.setColor(Color.RED);
	//	g.drawString(name, Math.round(x), Math.round(y));
		
	}
	
	//						   0      0     maxW    maxH     root
	public float calcPositions(float x, float y,float w, float h, long parentSize) {
		Iterator<Entry> iterator = this.files.iterator();
		if(w>h){
			this.x = x;
			this.y = y;
			this.w = w/parentSize*this.size;
			this.h = h;	
		}else{
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h/parentSize*this.size;
		}
		
		float shift = 0;
		while (iterator.hasNext()){
			Entry node = iterator.next();
			if(this.w>this.h){
				shift += node.calcPositions(x+shift, y, this.w, this.h, this.size);
			}else{
				shift += node.calcPositions(x, y+shift, this.w, this.h, this.size);
			}
			
			
		}
		if(w>h){
			return this.w;
		}else{
			return this.h;
		}
	}
	
} // end of class Dir