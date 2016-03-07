import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class Strategy implements Runnable{

	   
	    private boolean isTouche=false;
	    private boolean isfinish=false;
	    private Thread Sensorthread;
	    private Position finalpos;
	    private Robot robot;
	    Strategy()
			{	
				finalpos=new Position(189,48);
				Position pos=new Position(0,0,90);
				robot=new Robot(MotorPort.B,MotorPort.C,MotorPort.A,pos);
				Sensorthread=new Thread(new Runnable()
						{

							public void run() {
								// TODO Auto-generated method stub
								while(!isfinish)
								{
									if(robot.getTS().isPressed())
									{		
										System.out.println("catchPalte");
										robot.catchPalet();
									}
									
								}
								
							}
					
						});
						
						
			}
			public void run() {
				// TODO Auto-generated method stub
				
				Palet []palet_base=new Palet[10];
				Palet palet1=new Palet(88,178);
				Palet palet2=new Palet(56,149);
				Palet palet3=new Palet(120,30);
				Palet palet4=new Palet(34,67);
				Palet palet5=new Palet(67,89);
				Palet palet6=new Palet(78,93);
				Palet palet7=new Palet(12,34);
				Palet palet8=new Palet(65,87);
				Palet palet9=new Palet(17,36);
				Palet palet10=new Palet(100,30);				
				palet_base[0]=palet1;
				palet_base[1]=palet2;
				palet_base[2]=palet3;
				palet_base[3]=palet4;
				palet_base[4]=palet5;
				palet_base[5]=palet6;
				palet_base[6]=palet7;
				palet_base[7]=palet8;
				palet_base[8]=palet9;
				palet_base[9]=palet10;				
				int j=0;
				int compte=0;
				Sensorthread.start();
				while(!isfinish)
				{
					for(int i=0;i<10;i++)
					{
						//donner les positions des palets 
					}
					if(Button.ENTER.isDown())
					{
						isfinish=true;
					}
					int number =robot.meillerPalet(palet_base,robot.getPos());
					robot.arrivePalet(palet_base[number]);
					System.out.println("robot_position:"+robot.getPos().getX()+"robot_position:"+robot.getPos().getY());
					while(!robot.getisCatch())
					{					
						
					};
					robot.arrivePos(finalpos);
					System.out.println("robot_position:"+robot.getPos().getX()+"robot_position:"+robot.getPos().getY());
					while(!robot.test_erreur(finalpos));
					robot.dropPalet();
					palet_base[number].setNoter(true);
					for(j=0;j<10;j++)
					{
						
						if(palet_base[j].getNoter()==false)
						{
							isfinish=false;
							break;
						}
					}
					if(j==10)
					{
						isfinish=true;
					}										
				}
				System.out.println("fini");
			}

}
