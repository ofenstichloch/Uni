import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.Iterator;

class SingleFile extends Entry
{
	File file;


	SingleFile(String n, File f, long s, long a)
	{
		name = n;
		file = f;
		size = s;
		age = a;
	}


	public float calcPositions(float x, float y,float w, float h, long parentSize) {
		float ret;
			if(w>h){
				this.w = w/parentSize*this.size;
				this.h = h;
				this.x = x;
				this.y = y;
				ret=this.w;
			}else{
				this.h = h/parentSize*this.size;
				this.w = w;
				this.x = x;
				this.y = y;
				ret=this.h;
			}
			return ret;
		
	}
	
	
	void print(String prefix)
	{
		System.out.println(prefix + name + ":"+"		");
		fullname = prefix + name + ":" + size;
	}
	
	public void draw(Graphics g, long minage, long maxage){
		
		double colorIndex = (age-minage)/(double) (maxage-minage)*255;
		g.setColor(LOCS.rgb[(int) colorIndex]);
		g.fillRect(Math.round(x), Math.round(y), Math.round(w), Math.round(h));
		g.setColor(Color.YELLOW);
		g.drawRect(Math.round(x), Math.round(y), Math.round(w), Math.round(h));
		
	//	g.setColor(Color.RED);
	//	g.drawString(name, Math.round(x+10), Math.round(y+15));

	}
} // end of class SingleFile