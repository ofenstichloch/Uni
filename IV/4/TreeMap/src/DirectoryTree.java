import java.io.File;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class DirectoryTree extends Frame
{
	Dir files;
	static final int LINEAR = 0, LOG = 1, EXP = 2, SQRT = 3;
	static int scaleMode = SQRT;
	static long now = System.currentTimeMillis();
	static long maxage;
	static long minage;

	
	
	public static void main(String argv[])
	{
		String root;
		if (argv.length == 0)
			/*
			 * 
			 * HIER PFAD AENDERN
			 * HIER PFAD AENDERN 
			 * HIER PFAD AENDERN
			 * HIER PFAD AENDERN
			 * HIER PFAD AENDERN 
			 * HIER PFAD AENDERN
			 * HIER PFAD AENDERN 
			 * HIER PFAD AENDERN 
			 * HIER PFAD AENDERN 
			 */
			root = "/Users/alexanderlandmesser/Downloads";
		else
			root = argv[0];
		
		Dir d = getDirectoryTree(root, scaleMode);
		d.calcPositions(50,50,1000,600, d.size);
		//d.print();
		Window w = new Window();
		LOCS.drawScale(w.getGraphics(), 50, 650, 400, 20);
		d.draw(w.getGraphics(), minage, maxage);
		w.addMouseListener(new MouseListener() {

			
			@Override
			public void mouseReleased(MouseEvent e) {
				d.draw(w.getGraphics(), minage, maxage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				String name = d.getName(e.getX(),e.getY());
				Graphics g = w.getGraphics();
				g.setColor(Color.RED);
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.drawString(name, e.getX(), e.getY());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}


	/**
	 * Computes the directory tree starting at directory "startDir".
	 * A directory tree consists of inner nodes of type Dir and leaf nodes of type SingleFile.
	 * The size of each file and directory is computed using linear, logarithmic, etc. scale as 
	 * specified by the parameter "mode"
	 * @param startDir - starting at given directory
	 * @param mode - linear, logarithmic, etc. scale
	 * @return
	 */
	static Dir getDirectoryTree(String startDir, int mode)
	{
		scaleMode = mode;
		File f = new File(startDir);
		minage = now - f.lastModified();
		return getDirectoryTree(new File[] { f });
	}


	static Dir getDirectoryTree(File[] fa)
	{
		Vector<Entry> v = new Vector<Entry>();
		long size = 0;
		
		for (int i = 0; i < fa.length; i++)
		{
			File f = fa[i];
			if (f.isDirectory())
			{
				File[] sub_fa = f.listFiles();
				Dir df = getDirectoryTree(sub_fa);
				df.name = f.getName();
				v.add(df);
				size += df.size;
			}
			else
			{
				size += scale(f.length());
				long age = now - f.lastModified();
				if(age > maxage){
					maxage = age;
				}
				if(age < minage){
					minage = age;
				}
				v.add(new SingleFile(f.getName(), f, (int) scale(f.length()), age));
			}
		}

		return new Dir("", v, size);
	}

 static boolean doit = true;
 
	static float scale(long i)
	{
		return scale((float) i);
	}


	static float scale(int i)
	{
		return scale((float) i);
	}


	static float scale(float f)
	{
		switch (scaleMode)
		{
			case LINEAR:
				return f;
			case LOG:
				return (float) (Math.log(1 + f));
			case EXP:
				return (float) Math.exp(f);
			case SQRT:
				return (float) Math.sqrt(f);
			default:
				return f;
		}
	}

} // end of class DirectoryTree
