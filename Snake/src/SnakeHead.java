import org.newdawn.slick.geom.Rectangle;


public class SnakeHead {
	public Rectangle rec;
	int movement;
	int speed = 32;
	int dx = speed;
	int dy = 0;
	
	public SnakeHead(int x, int y){
		rec = new Rectangle(x,y,30,30);
		movement = 4;
	}
	
	public void move(int m){
		movement = m;
		if(movement == 1){
			dx = 0;
			dy = -speed;
		}else if(movement == 2){
			dx = -speed;
			dy = 0;
		}else if(movement == 3){
			dx = 0;
			dy = speed;
		}else if(movement == 4){
			dx = speed;
			dy = 0; 
		}
		
		rec.setX(rec.getX()+dx);
		rec.setY(rec.getY()+dy);
	}
	
	public int getX(){
		return (int) rec.getX();
	}

	public int getY(){
		return (int) rec.getY();
	}
}
