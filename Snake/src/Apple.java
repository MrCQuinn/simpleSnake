import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.Graphics;

public class Apple {
	Ellipse circle;
	public Apple(int x, int y){
		//random x and y
		 circle = new Ellipse(x,y,5,5);
	}
	
	public  void mydraw(Graphics g){
		g.draw(circle);
	}

}
