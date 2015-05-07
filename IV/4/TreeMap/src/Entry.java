import java.awt.Graphics;

class Entry
{
	long size;
	String name;
	String fullname;
	long age;
	float x, y, w, h;
	
	void print()
	{
		print("");
	}

	String getName(int x, int y){
		if(x > this.x && x < this.x + this.w){
			if(y > this.y && y < this.y+this.h){
				return this.name;
			}
		}
		return "";
	}

	void print(String prefix)
	{
		System.out.println("Error !!!");
	}

	public void draw(Graphics g, long minage, long maxage){
		g.setColor(LOCS.rgb[(int) (age/maxage*255)]);
		g.fillRect(Math.round(x), Math.round(y), Math.round(w), Math.round(h));
	}

	public float calcPositions(float x, float y, float w, float h, long parentSize) {
		return 0;
	}
} // end of class Entry