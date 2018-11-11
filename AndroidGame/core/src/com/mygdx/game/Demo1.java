package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Iterator;

import static com.mygdx.game.Board.toDrop;

public class Demo1 extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture background,bear,dolphin,bird,snail,dog,butterfly,frog;
	Sound select,fall,match,back;
	//Vector3 touchPos;
	static Rectangle ClickedPoint;
	Timer timer;
	private MyConsole console = MyConsole.getInstance();
	static Gem[] table = new Gem[64];
	static boolean shouldPreview=false;
	static int selectedId = -1;
	static boolean firstTime=true;
	static boolean shouldSwift=false;
	static int preview=0;
	static int swiftA=-1,swiftB=-1,iter=0,iter2=0;
	static boolean swiftBack=false;
	@Override
	public void create () {
		Board.initBoard(table);
		timer=new Timer();
		timer.start();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 806, 629);
		background = new Texture(Gdx.files.internal("board.png"));
		bear = new Texture(Gdx.files.internal("bear.png"));
		dolphin = new Texture(Gdx.files.internal("dolphin.png"));
		bird = new Texture(Gdx.files.internal("bird.png"));
		snail = new Texture(Gdx.files.internal("snail.png"));
		dog = new Texture(Gdx.files.internal("dog.png"));
		butterfly = new Texture(Gdx.files.internal("butterfly.png"));
		frog = new Texture(Gdx.files.internal("frog.png"));

		select = Gdx.audio.newSound(Gdx.files.internal("select.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("fall.wav"));
		match = Gdx.audio.newSound(Gdx.files.internal("match.wav"));
		//back = Gdx.audio.newSound(Gdx.files.internal("back.wav"));

		//Vector3 touchPos = new Vector3();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		//input
		if(Gdx.input.isTouched()) {
			Vector3 touchPos=new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			ClickedPoint=new Rectangle();
			ClickedPoint.x=touchPos.x;
			ClickedPoint.y=600-touchPos.y;
		}
		//logic
		Rectangle point = console.getClickedPoint();
		if(point!=null)//select gems
		{
			for (int i = 0; i < 64; i++) {
				if (table[i].isAt(point))
				{
					table[i].toggleFocus();
					//select.playSound();
					if (selectedId != -1)//selected
					{
						shouldSwift=table[i].swiftGem(table[selectedId]);
						if(shouldSwift)
						{
							swiftA=selectedId;
							swiftB=i;
							iter=0;
							swiftBack=false;
						}
						table[i].toggleFocus();
						table[selectedId].toggleFocus();
						selectedId=-1;
					}
					else if (table[i].isSelected())
						selectedId = i;
				}
			}
		}
		if(iter==13)//end swift animation
		{
			if(preview<2)
			{
				table[Board.previewB].swiftGem(table[Board.previewA]);
				swiftA=Board.previewB;
				swiftB=Board.previewA;
				iter=0;
				preview=2;
			}
			else if(preview>2&&iter2==0&&Board.checkChain(table))
			{
				//match.playSound();
				//console.idle(100);
				//fall.playSound();
			}
			else preview++;
		}
		if(iter2==13)//end one elimination
		{
			iter2=0;
			Board.toDrop.clear();
			if(Board.checkChain(table))
			{//check again for cascade
				//match.playSound();
				//console.idle(100);
				//fall.playSound();
			}
		}
		//predraw
		console.drawText(60, 150, "[TIME]");
		console.drawText(60,200,timer.getTimeString());
		console.drawText(60, 250, "[SCORE]");
		console.drawText(60, 280, ""+Board.getScore());
		for (int i = 0; i < 64; i++)
			table[i].display();
		if(shouldSwift&&iter++<13)//swift iteration
		{
			table[swiftA].swiftAnimation(table[swiftB], iter);
			if(preview>2&&iter==13&&Board.checkChain(table)==false&&swiftBack==false)
			{
				shouldSwift=table[swiftA].swiftGem(table[swiftB]);
				if(shouldSwift&&swiftBack==false)
				{
					iter=0;
					swiftBack=true;
				}
				selectedId=-1;
			}
		}
		else if(iter2++<13)//drop iteration
		{
			for (Iterator it=toDrop.iterator();it.hasNext();) {
				int value= (Integer)it.next();
				table[value].dropAnimation(iter2);
			}
		}
		//display
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0);
		for (int i=0; i<MyConsole.textToDraw.size(); i++) {
			MyTextPair value = (MyTextPair)MyConsole.textToDraw.get(i);
			CharSequence str=value.getStr();
			BitmapFont font=new BitmapFont();
			font.draw(batch, str, value.getX(), 535-value.getY());
		}
		for (int i=0; i<MyConsole.imgToDraw.size(); i++) {
			MyImgPair value = (MyImgPair)MyConsole.imgToDraw.get(i);
			Texture img=new Texture(Gdx.files.internal(value.getImg()));
			batch.draw(img,value.getX(),535-value.getY());
		}
		MyConsole.textToDraw.clear();
		MyConsole.imgToDraw.clear();
		batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
	public static void clearClickedPoint()
	{
		ClickedPoint=null;
	}
}
