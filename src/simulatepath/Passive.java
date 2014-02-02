package simulatepath;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;

public class Passive extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static double mediumDimensionX;
	static double mediumDimensionY;
	static double mediumDimensionZ;
	static double maxSimulationTime;
	static double stepLengthX;
	static double stepLengthY;
	static double stepLengthZ;
	static Position reciever = new Position();
	static Position sender = new Position();
	static double recieverRadius;
	static int noOfMolecules;
	static String inFile = "input0.dat";
	static String outFile = "output";
	static int[] steparr = {1,0,-1};
	static String delim = ",";
	private static double distSendReciever;

	public static void main(String[] args) throws IOException{
		if(args.length==0)
			readParams(inFile);
		else
			readParams(args[0]);
//		final ArrayList<Molecule> mols = new ArrayList<Molecule>(noOfMolecules);
//		final ArrayList<FileWriter> writers = new ArrayList<FileWriter>(noOfMolecules);
		for(int i=0;i<noOfMolecules;i++){
			final Position p = new Position();
			p.setX(sender.getX());
			p.setY(sender.getY());
			p.setZ(sender.getZ());
	//		mols.add(i,new Molecule(stepLengthX, stepLengthY, stepLengthZ,p));
	//		writers.add(i, new FileWriter(new File(outFile+i+".txt")));
			final int j = i;
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//(new Passive()).simulateThis(mols.get(j),writers.get(j));
					try {
						(new Passive()).simulateThis(new Molecule(stepLengthX, stepLengthY, stepLengthZ,p),
								new FileWriter(new File(outFile+j+".txt")));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static void readParams(String inFile) throws IOException{
		boolean flag = true;
		String line;
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		while((line = br.readLine())!=null){
			String param = "";
			if(!line.equals(""))
				param = line.substring(line.indexOf(" ")+1).trim();
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
			else if(line.startsWith("noOfMolecules")){
				noOfMolecules = Integer.parseInt(param);
			}
			else if(line.startsWith("distSendReciever")){
				distSendReciever = Double.parseDouble(param);
				sender.setX(-distSendReciever/2);
				sender.setY(0);
				sender.setZ(0);
				reciever.setX(distSendReciever/2);
				reciever.setY(0);
				reciever.setZ(0);
				flag = false;
			}
			else if(line.startsWith("senderPosition") && flag){
				sender.setX(Double.parseDouble(param.substring(1,param.indexOf(delim))));
				sender.setY(Double.parseDouble(param.substring(
						param.indexOf(delim)+1, 
						param.indexOf(delim, param.indexOf(delim)+1)
						)));
				sender.setZ(Double.parseDouble(param.substring(
						param.indexOf(delim, param.indexOf(delim)+1)+1, 
						param.length()-1
						)));
			}
			else if(line.startsWith("recieverCenter") && flag){
				reciever.setX(Double.parseDouble(param.substring(1,param.indexOf(delim))));
				reciever.setY(Double.parseDouble(param.substring(
						param.indexOf(delim)+1, 
						param.indexOf(delim, param.indexOf(delim)+1)
						)));
				reciever.setZ(Double.parseDouble(param.substring(
						param.indexOf(delim, param.indexOf(delim)+1)+1, 
						param.length()-1
						)));
			}
			else if(line.startsWith("recieverRadius")){
				recieverRadius = Double.parseDouble(param);				
			}
		}
		br.close();
	}

	public static boolean hasReachDestination(Position curpos){
		boolean b = false;
		if(curpos.getDistance(reciever) <= recieverRadius)
			b=true;
		return b;
	}

	private static void checkBoundary(Position curpos) {
		if(curpos.getX() > mediumDimensionX/2)
			curpos.setX(mediumDimensionX/2);
		if(curpos.getX() < -mediumDimensionX/2)
			curpos.setX(-mediumDimensionX/2);
		if(curpos.getY() > mediumDimensionY/2)
			curpos.setY(mediumDimensionY/2);
		if(curpos.getY() < -mediumDimensionY/2)
			curpos.setY(-mediumDimensionY/2);
		if(curpos.getZ() > mediumDimensionZ/2)
			curpos.setZ(mediumDimensionZ/2);
		if(curpos.getZ() < -mediumDimensionZ/2)
			curpos.setZ(-mediumDimensionZ/2);
	}

	private void simulateThis(Molecule molecule, FileWriter writer){
		String newline = "";
		long time = System.nanoTime();
		boolean reachFlag = false;
		try {
			while(System.nanoTime()-time < (long)maxSimulationTime){
				Position curpos = molecule.getPosition();
				writer.write(newline + curpos.getX() +
						delim + curpos.getY() +
						delim + curpos.getZ());
				newline = "\n";
				if(hasReachDestination(curpos)){
					reachFlag = true;
					break;
				}
				curpos.setX(curpos.getX() + molecule.getStepLengthX()*steparr[(int) (Math.random()*3)]);
				curpos.setY(curpos.getY() + molecule.getStepLengthY()*steparr[(int) (Math.random()*3)]);
				curpos.setZ(curpos.getZ() + molecule.getStepLengthZ()*steparr[(int) (Math.random()*3)]);
				checkBoundary(curpos);
				writer.flush();
			}
			writer.flush();
			writer.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(reachFlag)
			System.out.println(":) Hooray this molecule reached to destination");
		else 
			System.out.println(":( This molecule couldn't reach its destination");

	}
	public void paintComponent(Graphics g){

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}