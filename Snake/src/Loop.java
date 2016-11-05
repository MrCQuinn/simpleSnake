import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.Image;

public class Loop extends BasicGame {
	SnakeHead snake;
	ArrayList<Rectangle> body;
	Ellipse apple;
	Image wasted;
	boolean dead = false;
	
	float dx;
	float dy;

    float bodyx;
    float bodyy;
    
    int RESTING_FRAMES = 10;
	
    int m;
    
    int counter = 60;
	
	public Loop()
    {
        super("Snake AI");
    }
 
	
    @Override
    public void init(GameContainer gc) 
			throws SlickException {
    	//initialize head
    	snake = new SnakeHead(30,30);
    	//initialize apple
    	apple = new Ellipse(200,200,5,5);
    	//initialize array of body squares
    	body = new ArrayList<Rectangle>();
    	//image for when snake dies
    	wasted = new Image("img/wasted.png");
    }
 
    @Override
    public void update(GameContainer gc, int delta) 
			throws SlickException     
	{
    	//check for input
    	Input input = gc.getInput();
    	
    	//utilize input
    	if(input.isKeyDown(Input.KEY_RIGHT)){
    		m = 4;
    	}else if(input.isKeyDown(Input.KEY_LEFT)){
    		m = 2;
    	}else if(input.isKeyDown(Input.KEY_DOWN)){
    		m = 3;
    	}else if(input.isKeyDown(Input.KEY_UP)){
    		m = 1;
    	}
    	
    	//check if head of snake intercepts apple
    	if(snake.rec.intersects(apple) || snake.rec.contains(apple)){
    		grow();
    	}
    	
    	
    	//check if body of snake intercepts apple
    	for(int j = body.size()-1; j > 0; j--){
    		if(body.get(j).intersects(apple) || body.get(j).intersects(apple)){
    			grow();
    		}
    	}
    	
    	//check for death by walls
    	if(snake.getX() < 25 || snake.getX() > 795 || snake.getY() < -5 || snake.getY() > 585){
    		die();
    	}
    	
    	//move snake head every 10 frames
    	if(RESTING_FRAMES > 0){
    		RESTING_FRAMES--;
    	}else{	    	
    		
    		//move body pieces
    		for(int i = body.size()-1; i > 0; i--){
    			body.get(i).setX(body.get(i-1).getX());
    			body.get(i).setY(body.get(i-1).getY());
    		}
    		
    		//move neck
			if(body.size()>0){
    			body.get(0).setX(snake.getX());
    			body.get(0).setY(snake.getY());
			}
			
			//move head
    		if(!dead){
    			snake.move(m);
    		}
    		
    		//check for death by eating self
	    	for(int i = body.size()-1; i > 0; i--){
	    		if(snake.rec.intersects(body.get(i))){
	    			die();
	    		}
	    	}
    		RESTING_FRAMES = 10;
    	}
    	
    	//cap frame rate
    	try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    public void render(GameContainer gc, Graphics g) 
			throws SlickException 
    {
    	//draw snake head
    	g.draw(snake.rec);
    	
    	//draw body bits
    	for(int i = body.size()-1; i >= 0; i--){
    		g.draw(body.get(i));
    	}
    	
    	//draw apple
    	g.draw(apple);
    	
    	//"wasted" death splash
    	if(dead){
    		if(counter>0){
    			wasted.draw(257,157,1.0f);
    			counter--;
    		}else{
    			dead = false;
    			counter = 60;
    		}
    	}
    }
    
    // grow, called when intersection with apple occurs
    public void grow(){
    	//random location on screen for new apple
    	apple = new Ellipse((int)(Math.random() * 800),(int)(Math.random() * 600), 5,5);
    	//append list of body bits
    	body.add(new Rectangle(snake.getX(), snake.getY(), 30, 30));
    }
    
    public void die(){
    	dead = true;
    	//reset snake to one piece moving right from default position
    	body.clear();
    	snake.rec.setX(30);
    	snake.rec.setY(30);
    	m = 4;
    }
    //main function creates gamecontainer and gui 800x600 
    public static void main(String[] args) 
			throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new Loop());
         app.setDisplayMode(800, 600, false);
         app.start();
    }
}


