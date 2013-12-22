package passivePropagation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class MovMolecules {
	
	static double stepLengthX;
	static double stepLengthY;
	static double stepLengthZ;
	static double mediumDimensionX;
	static double mediumDimensionY;
	static double mediumDimensionZ;
	static double maxSimulationTime;
	static double recieverRadius;
	
	static String inFile = "input.dat";
	static String outFile = "output.dat";
	static Position reciever = new Position();	
	static Position sender = new Position();
	static Position curpos = new Position();
	static int[] steparr = {1,0,-1};
	static String delim = ",";
	static String newline = "";
	/*static ArrayList<Double> arrx = new ArrayList<Double>();
	static ArrayList<Double> arry= new ArrayList<Double>();
	static ArrayList<Double> arrz= new ArrayList<Double>();*/
	public static void main(String[] args) throws IOException{
	
		boolean reachFlag = false;
		if(args.length==0)
			readParams(inFile);
		else
			readParams(args[0]);
		FileWriter writer= new FileWriter(new File(outFile));
		
		long time = System.nanoTime();
		while(System.nanoTime()-time < (long)maxSimulationTime){
			writer.write(newline+curpos.x+delim+curpos.y+delim+curpos.z);
			/*arrx.add(curpos.x);
			arry.add(curpos.y);
			arrz.add(curpos.z);*/
			newline = "\n";
			curpos.x = curpos.x + stepLengthX*steparr[(int) (Math.random()*3)];
			curpos.y = curpos.y + stepLengthY*steparr[(int) (Math.random()*3)];
			curpos.z = curpos.z + stepLengthZ*steparr[(int) (Math.random()*3)];
			
			if(hasReachDestination(curpos)){
				writer.write(newline+curpos.x+delim+curpos.y+delim+curpos.z);
				reachFlag = true;
				break;
			}
			
			if(hasReachedBoundry(curpos)){
				writer.write(newline+curpos.x+delim+curpos.y+delim+curpos.z);
				newline = "\n";
				curpos=getNewPosition(curpos);
				
			}
			
			
			
			writer.flush();
		}
		writer.flush();
		writer.close();
		if(reachFlag)
			System.out.println("Hooray the molecule reached to destination");
		else {
			System.out.println(":( the molecule couldn't reach its destination");
		}
		System.out.println();
			/*System.out.println("x points"+arrx);
			System.out.println("y"+arry);
			System.out.println("z"+arrz);*/
		
	}
	
	
	


	public static void readParams(String inFile) throws IOException{
		String line;
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		while((line = br.readLine())!=null && !line.equals("")){
			String param = line.substring(line.indexOf(" ")+1);
			if(line.startsWith("stepLengthX")){
				stepLengthX = Double.parseDouble(param);
			}
			else if(line.startsWith("stepLengthY")){
				stepLengthY = Double.parseDouble(param);
			}
			else if(line.startsWith("stepLengthZ")){
				stepLengthZ = Double.parseDouble(param);
			}
			else if(line.startsWith("mediumDimensionX")){
				mediumDimensionX = Double.parseDouble(param);
			}
			else if(line.startsWith("mediumDimensionY")){
				mediumDimensionY = Double.parseDouble(param);				
			}
			else if(line.startsWith("mediumDimensionZ")){
				mediumDimensionZ = Double.parseDouble(param);
			}
			else if(line.startsWith("maxSimulationTime")){
				maxSimulationTime = Double.parseDouble(param);
			}
			else if(line.startsWith("senderPosition")){
				sender.x = Double.parseDouble(param.substring(1,param.indexOf(delim)));
				sender.y = Double.parseDouble(param.substring(
						param.indexOf(delim)+1, 
						param.indexOf(delim, param.indexOf(delim)+1)
						));
				sender.z = Double.parseDouble(param.substring(
						param.indexOf(delim, param.indexOf(delim)+1)+1, 
						param.length()-1
						));
				curpos.x = sender.x;
				curpos.y = sender.y;
				curpos.z = sender.z;
			}
			else if(line.startsWith("recieverCenter")){
				reciever.x = Double.parseDouble(param.substring(1,param.indexOf(delim)));
				reciever.y = Double.parseDouble(param.substring(
						param.indexOf(delim)+1, 
						param.indexOf(delim, param.indexOf(delim)+1)
						));
				reciever.z = Double.parseDouble(param.substring(
						param.indexOf(delim, param.indexOf(delim)+1)+1, 
						param.length()-1
						));
			}
			else if(line.startsWith("recieverRadius")){
				recieverRadius = Double.parseDouble(line.substring(line.indexOf(" ")));				
			}
		}
		br.close();
	}
	public static boolean hasReachDestination(Position curpos){
		boolean b = false;
		if(Math.sqrt((reciever.x-curpos.x)*(reciever.x-curpos.x)
				+(reciever.y-curpos.y)*(reciever.y-curpos.y)
				+(reciever.z-curpos.z)*(reciever.z-curpos.z)) <= recieverRadius)
			b=true;
		return b;
	}
	
	private static Position getNewPosition(Position curpos) {
		//Position curposnew=new Position();
		if(curpos.x<0){
			curpos.x=curpos.x+1;
		}
		if(curpos.y<0){
			curpos.y=curpos.y+1;
		}
		if(curpos.z<0){
			curpos.z=curpos.z+1;
		}
		if(curpos.x>100){
			curpos.x=curpos.x-1;
		}
		if(curpos.y>150){
			curpos.y=curpos.y-1;
		}
		if(curpos.z>300){
			curpos.z=curpos.z-1;
		}
		
		
		return curpos;
	}

	
	private static boolean hasReachedBoundry(Position curpos) {
		boolean boundryFlag=false;
		if(curpos.x<=0 |curpos.x>=100 | curpos.y<=0 |curpos.y>=50 | curpos.z<=0 | curpos.z>=300){
			boundryFlag=true;
		}
		return boundryFlag;
	}
	
}
class Position{
	double x;
	double y;
	double z;
}