import java.awt.Color;
import java.awt.Graphics;

/**
 * The following class can be used to map values from 0 to 255 to colors on an optimal color scale.
 * For the value i use the color LOCS.rgb[i].
 * 
 * Linear optimal color scale according to Dr. Haim Levkowitz, UMass Lowell (perceptually
 * linearized).
 */
class LOCS
{
	static void drawScale(Graphics g, int x, int y, int width, int height)
	{
		Color old = g.getColor();
		for (int i = 0; i < 256; i++)
		{
			g.setColor(LOCS.rgb[i]);
			g.fillRect(x + i * 2, y, 2, height);
		}
		g.setColor(old);
	}


	static public int size = 256;
	static public Color[] rgb = { new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0),
			new Color(1, 0, 0), new Color(2, 0, 0), new Color(2, 0, 0), new Color(3, 0, 0),
			new Color(3, 0, 0), new Color(4, 0, 0), new Color(5, 0, 0), new Color(5, 0, 0),
			new Color(6, 0, 0), new Color(7, 0, 0), new Color(7, 0, 0), new Color(8, 0, 0),
			new Color(9, 0, 0), new Color(9, 0, 0), new Color(10, 0, 0), new Color(11, 0, 0),
			new Color(12, 0, 0), new Color(13, 0, 0), new Color(14, 0, 0), new Color(15, 0, 0),
			new Color(16, 0, 0), new Color(17, 0, 0), new Color(18, 0, 0), new Color(19, 0, 0),
			new Color(20, 0, 0), new Color(21, 0, 0), new Color(22, 0, 0), new Color(23, 0, 0),
			new Color(25, 0, 0), new Color(26, 0, 0), new Color(27, 0, 0), new Color(28, 0, 0),
			new Color(30, 0, 0), new Color(31, 0, 0), new Color(33, 0, 0), new Color(34, 0, 0),
			new Color(35, 0, 0), new Color(37, 0, 0), new Color(39, 0, 0), new Color(40, 0, 0),
			new Color(43, 0, 0), new Color(45, 0, 0), new Color(46, 0, 0), new Color(49, 0, 0),
			new Color(51, 0, 0), new Color(53, 0, 0), new Color(54, 0, 0), new Color(56, 0, 0),
			new Color(58, 0, 0), new Color(60, 0, 0), new Color(62, 0, 0), new Color(64, 0, 0),
			new Color(67, 0, 0), new Color(69, 0, 0), new Color(71, 0, 0), new Color(74, 0, 0),
			new Color(76, 0, 0), new Color(80, 0, 0), new Color(81, 0, 0), new Color(84, 0, 0),
			new Color(86, 0, 0), new Color(89, 0, 0), new Color(92, 0, 0), new Color(94, 0, 0),
			new Color(97, 0, 0), new Color(100, 0, 0), new Color(103, 0, 0), new Color(106, 0, 0),
			new Color(109, 0, 0), new Color(112, 0, 0), new Color(115, 0, 0), new Color(117, 0, 0),
			new Color(122, 0, 0), new Color(126, 0, 0), new Color(128, 0, 0), new Color(131, 0, 0),
			new Color(135, 0, 0), new Color(135, 0, 0), new Color(135, 1, 0), new Color(135, 2, 0),
			new Color(135, 3, 0), new Color(135, 4, 0), new Color(135, 6, 0), new Color(135, 6, 0),
			new Color(135, 8, 0), new Color(135, 9, 0), new Color(135, 10, 0),
			new Color(135, 11, 0), new Color(135, 13, 0), new Color(135, 13, 0),
			new Color(135, 15, 0), new Color(135, 17, 0), new Color(135, 17, 0),
			new Color(135, 19, 0), new Color(135, 21, 0), new Color(135, 22, 0),
			new Color(135, 23, 0), new Color(135, 25, 0), new Color(135, 26, 0),
			new Color(135, 27, 0), new Color(135, 29, 0), new Color(135, 31, 0),
			new Color(135, 32, 0), new Color(135, 33, 0), new Color(135, 35, 0),
			new Color(135, 36, 0), new Color(135, 38, 0), new Color(135, 40, 0),
			new Color(135, 42, 0), new Color(135, 44, 0), new Color(135, 46, 0),
			new Color(135, 47, 0), new Color(135, 49, 0), new Color(135, 51, 0),
			new Color(135, 52, 0), new Color(135, 54, 0), new Color(135, 56, 0),
			new Color(135, 57, 0), new Color(135, 59, 0), new Color(135, 62, 0),
			new Color(135, 63, 0), new Color(135, 65, 0), new Color(135, 67, 0),
			new Color(135, 69, 0), new Color(135, 72, 0), new Color(135, 73, 0),
			new Color(135, 76, 0), new Color(135, 78, 0), new Color(135, 80, 0),
			new Color(135, 82, 0), new Color(135, 84, 0), new Color(135, 87, 0),
			new Color(135, 88, 0), new Color(135, 90, 0), new Color(135, 93, 0),
			new Color(135, 95, 0), new Color(135, 98, 0), new Color(135, 101, 0),
			new Color(135, 103, 0), new Color(135, 106, 0), new Color(135, 107, 0),
			new Color(135, 110, 0), new Color(135, 113, 0), new Color(135, 115, 0),
			new Color(135, 118, 0), new Color(135, 121, 0), new Color(135, 124, 0),
			new Color(135, 127, 0), new Color(135, 129, 0), new Color(135, 133, 0),
			new Color(135, 135, 0), new Color(135, 138, 0), new Color(135, 141, 0),
			new Color(135, 144, 0), new Color(135, 148, 0), new Color(135, 150, 0),
			new Color(135, 155, 0), new Color(135, 157, 0), new Color(135, 160, 0),
			new Color(135, 163, 0), new Color(135, 166, 0), new Color(135, 170, 0),
			new Color(135, 174, 0), new Color(135, 177, 0), new Color(135, 180, 0),
			new Color(135, 184, 0), new Color(135, 188, 0), new Color(135, 192, 0),
			new Color(135, 195, 0), new Color(135, 200, 0), new Color(135, 203, 0),
			new Color(135, 205, 0), new Color(135, 210, 0), new Color(135, 214, 0),
			new Color(135, 218, 0), new Color(135, 222, 0), new Color(135, 226, 0),
			new Color(135, 231, 0), new Color(135, 236, 0), new Color(135, 239, 0),
			new Color(135, 244, 0), new Color(135, 249, 0), new Color(135, 254, 0),
			new Color(135, 255, 1), new Color(135, 255, 5), new Color(135, 255, 10),
			new Color(135, 255, 15), new Color(135, 255, 20), new Color(135, 255, 23),
			new Color(135, 255, 28), new Color(135, 255, 33), new Color(135, 255, 38),
			new Color(135, 255, 43), new Color(135, 255, 45), new Color(135, 255, 49),
			new Color(135, 255, 54), new Color(135, 255, 59), new Color(135, 255, 65),
			new Color(135, 255, 70), new Color(135, 255, 74), new Color(135, 255, 80),
			new Color(135, 255, 84), new Color(135, 255, 90), new Color(135, 255, 95),
			new Color(135, 255, 98), new Color(135, 255, 104), new Color(135, 255, 110),
			new Color(135, 255, 116), new Color(135, 255, 120), new Color(135, 255, 125),
			new Color(135, 255, 131), new Color(135, 255, 137), new Color(135, 255, 144),
			new Color(135, 255, 149), new Color(135, 255, 154), new Color(135, 255, 158),
			new Color(135, 255, 165), new Color(135, 255, 172), new Color(135, 255, 179),
			new Color(135, 255, 186), new Color(135, 255, 191), new Color(135, 255, 198),
			new Color(135, 255, 203), new Color(135, 255, 211), new Color(135, 255, 216),
			new Color(135, 255, 224), new Color(135, 255, 232), new Color(135, 255, 240),
			new Color(135, 255, 248), new Color(135, 255, 254), new Color(135, 255, 255),
			new Color(140, 255, 255), new Color(146, 255, 255), new Color(153, 255, 255),
			new Color(156, 255, 255), new Color(161, 255, 255), new Color(168, 255, 255),
			new Color(172, 255, 255), new Color(177, 255, 255), new Color(182, 255, 255),
			new Color(189, 255, 255), new Color(192, 255, 255), new Color(199, 255, 255),
			new Color(204, 255, 255), new Color(210, 255, 255), new Color(215, 255, 255),
			new Color(220, 255, 255), new Color(225, 255, 255), new Color(232, 255, 255),
			new Color(236, 255, 255), new Color(240, 255, 255), new Color(248, 255, 255),
			new Color(255, 255, 255) };
} // end of class LOCS