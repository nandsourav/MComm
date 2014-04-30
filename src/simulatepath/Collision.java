package simulatepath;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JPanel;

public class Collision extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static double mediumDimensionX;
	static double mediumDimensionY;
	static double mediumDimensionZ;
	static double maxSimulationTime;
	static double maxSimulationStep;
	static double stepLengthX;
	static double stepLengthY;
	static double stepLengthZ;
	static Position reciever = new Position();
	static Position sender = new Position();
	static double recieverRadius;
	static int noOfMolecules;
	static String inFile = "input0.dat";
	static String reachFile = "dest";
	static String outFile = "output";
	static int[] steparr = {1,0,-1};
	static String delim = ",";
	private static double distSendReciever;
	private static double velRail;
	private static double probDrail;
	private static MicroTubule microtubule;
	private static ArrayList<MicroTubule> listOfMicroTubule = new ArrayList<MicroTubule>();
	private static double radius;
	static boolean generateOutputFile = false;
	public static void main(String[] args) throws IOException{

		if(args.length==0)
			readParams(inFile);
		else
			readParams(args[0]);
		final FileWriter fileWriter= new FileWriter(new File(reachFile+".txt"));
		final ArrayList<Molecule> mols = new ArrayList<Molecule>(noOfMolecules);
		final Map<Molecule,FileWriter> writers = new Hashtable<Molecule,FileWriter>(noOfMolecules);
		//FileWriter writer= new FileWriter(new File(outFile));
		if(generateOutputFile){
			new File("output").mkdir();
		}
		for(int i=0;i<noOfMolecules;i++){
			final Position p = new Position();
			p.setX(sender.getX());
			p.setY(sender.getY());
			p.setZ(sender.getZ());
			//final int j = i;
			Molecule temp = new Molecule(stepLengthX, stepLengthY, stepLengthZ, p,velRail,radius);
			mols.add(i,temp);
			if(generateOutputFile){
				writers.put(temp,new FileWriter(new File("output/"+outFile + i +".txt")));
			}
		}
		if(generateOutputFile){
			(new Collision()).simulatePropagation(mols, fileWriter, writers);
		}
		else{
			(new Collision()).simulatePropagation(mols,fileWriter, null);
		}

		//			writers.add(i, new FileWriter(new File(outFile+i+".txt")));
		//			javax.swing.SwingUtilities.invokeLater(new Runnable() {
		//				public void run() {
		//						try {
		//							if(generateOutputFile){
		//								new File("output").mkdir();
		//								(new Collision()).simulatePropagation(new Molecule(stepLengthX, stepLengthY, stepLengthZ, 
		//										p,velRail,radius), fileWriter, new FileWriter(new File("output/"+outFile + j +".txt")));
		//							}
		//							else{
		//								(new Collision()).simulatePropagation(new Molecule(stepLengthX, stepLengthY, stepLengthZ, 
		//										p,velRail,radius),fileWriter);
		//							}
		//								
		//					} catch (IOException e) {
		//						// TODO Auto-generated catch block
		//						e.printStackTrace();
		//					}
		//				}
		//			});
		//		}
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

			else if (line.startsWith("maxSimulationStep")){
				maxSimulationStep = Double.parseDouble(param);

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
			else if(line.startsWith("velRail")){
				velRail = Double.parseDouble(param);				
			}
			else if(line.startsWith("probDrail")){
				probDrail = Double.parseDouble(param);				
			}
			else if(line.startsWith("plusEndCentre")){
				microtubule = new MicroTubule();
				microtubule.setPlusEndCentre(new Position());
				listOfMicroTubule.add(microtubule);
				microtubule.getPlusEndCentre().setX(Double.parseDouble(param.substring(1,param.indexOf(delim))));
				microtubule.getPlusEndCentre().setY(Double.parseDouble(param.substring(
						param.indexOf(delim)+1, 
						param.indexOf(delim, param.indexOf(delim)+1)
						)));
				microtubule.getPlusEndCentre().setZ(Double.parseDouble(param.substring(
						param.indexOf(delim, param.indexOf(delim)+1)+1, 
						param.length()-1
						)));
			}
			else if(line.startsWith("minusEndCentre")){
				microtubule.setMinusEndCentre(new Position());
				microtubule.getMinusEndCentre().setX(Double.parseDouble(param.substring(1,param.indexOf(delim))));
				microtubule.getMinusEndCentre().setY(Double.parseDouble(param.substring(
						param.indexOf(delim)+1, 
						param.indexOf(delim, param.indexOf(delim)+1)
						)));
				microtubule.getMinusEndCentre().setZ(Double.parseDouble(param.substring(
						param.indexOf(delim, param.indexOf(delim)+1)+1, 
						param.length()-1
						)));
			}
			else if(line.startsWith("radiusMicroTubule")){
				microtubule.setRadiusMicroTubule(Double.parseDouble(param));				
			}
			else if (line.startsWith("radiusOfMolecule")){
				radius = Double.parseDouble(param);
			}

			else if (line.startsWith("OutputFile On")){
				generateOutputFile = true;
			}

		}
		br.close();
	}

	public static boolean hasReachDestination(Molecule mol){
		boolean b = false;
		if(mol.getPosition().getDistance(reciever) <= recieverRadius+mol.getRadius())
			b=true;
		return b;
	}

	private static void checkBoundary(Position curpos, double rad) {
		if(curpos.getX()+ rad> mediumDimensionX/2)
			curpos.setX(mediumDimensionX/2);
		if(curpos.getX()-rad < -mediumDimensionX/2)
			curpos.setX(-mediumDimensionX/2);
		if(curpos.getY()+rad > mediumDimensionY/2)
			curpos.setY(mediumDimensionY/2);
		if(curpos.getY()-rad < -mediumDimensionY/2)
			curpos.setY(-mediumDimensionY/2);
		if(curpos.getZ()+rad > mediumDimensionZ/2)
			curpos.setZ(mediumDimensionZ/2);
		if(curpos.getZ()-rad < -mediumDimensionZ/2)
			curpos.setZ(-mediumDimensionZ/2);
	}

	private void simulatePropagation(ArrayList<Molecule> listOfMolecule, FileWriter writer, Map<Molecule, FileWriter> writers){
		String newline = "";
		long time = System.nanoTime();
		long elapsed = 0;
		double runStep = maxSimulationTime;
		Position curpos = null;
		//double distance = distSendReciever!=0?distSendReciever:;
		if(maxSimulationStep>0){
			runStep = maxSimulationStep;
		}
		for(Molecule molecule: listOfMolecule){
			curpos = molecule.getPosition();
			if(probDrail!=0){
				if(molecule.getCurrentMicrotubule()==null){
					for(MicroTubule mt : listOfMicroTubule){
						Position p = getInitPointOnMicrotubule(mt,curpos);
						if(p!=null){
							molecule.setCurrentMicrotubule(mt);
							molecule.setPosition(p);
							break;
						}
					}
				}
			}
			else{
				break;
			}
		}
		try {	
			while((elapsed=(maxSimulationStep<=0)?(System.nanoTime()-time):(elapsed+1)) < (long)runStep){
				for(Molecule molecule:listOfMolecule){
					if(!molecule.isReachFlag()){
						curpos = molecule.getPosition();
						//molecule.setReachTime(molecule.getReachTime()+1);
						if(generateOutputFile){
							writers.get(molecule).write(newline + curpos.getX() +
									delim + curpos.getY() +
									delim + curpos.getZ());
							newline = "\n";
							writers.get(molecule).flush();
						}
						if(hasReachDestination(molecule)){
							if(maxSimulationStep<=0){
								molecule.setReachTime(System.nanoTime()-time);
							}
							else{
								molecule.setReachTime(elapsed);
							}
							molecule.setReachFlag(true);
							System.out.println(":) Hooray this molecule reached to destination");
							try {
								writer.write(elapsed+"index\n");
								writer.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							continue;
						}
						if(probDrail!=0){   //Active simulation starts
							if(molecule.getCurrentMicrotubule()!=null){
								if(hasDRailed()){
									//System.out.println("off rail");
									molecule.setCurrentMicrotubule(null);
									Position newPos = new Position();
									newPos.setX(curpos.getX() + molecule.getStepLengthX()*steparr[(int) (Math.random()*3)]);
									newPos.setY(curpos.getY() + molecule.getStepLengthY()*steparr[(int) (Math.random()*3)]);
									newPos.setZ(curpos.getZ() + molecule.getStepLengthZ()*steparr[(int) (Math.random()*3)]);
									checkBoundary(newPos,molecule.getRadius());
									Position nextPos = checkRailPos(curpos,newPos,molecule);
									if(nextPos!=null){
										//distance = curpos.getDistance(reciever);
										if(molecule.check(nextPos,listOfMolecule)){
											molecule.setPosition(nextPos);
											System.out.println("On Rail");
										}
										else{
											//System.out.println("Collision happened in random space1");
										}
									}
								}
								else{
									Position plusend = molecule.getCurrentMicrotubule().getPlusEndCentre();
									Position minusend = molecule.getCurrentMicrotubule().getMinusEndCentre();
									//Position newPos = new Position();
									Position perp = new Position();	
									double x2 = plusend.getX();
									double y2 = plusend.getY();
									double z2 = plusend.getZ();
									double x1 = minusend.getX();
									double y1 = minusend.getY();
									double z1 = minusend.getZ();
									double d = curpos.getX();
									double e = curpos.getY();
									double f = curpos.getZ();

									double t = ((x2 - d)*(x2-x1) +
											(y2 - e)*(y2-y1) +
											(z2 - f)*(z2-z1)) / ((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) + (z2-z1)*(z2-z1));
									perp.setX(d + (x2-x1)*t);
									perp.setY(e + (y2-y1)*t);
									perp.setZ(f + (z2-z1)*t);
									double distance = perp.getDistance(curpos);
									Position temp = new Position((velRail*perp.getX() + (distance-velRail)*curpos.getX())/distance,
											(velRail*perp.getY() + (distance-velRail)*curpos.getY())/distance,
											(velRail*perp.getZ() + (distance-velRail)*curpos.getZ())/distance);
									if(molecule.check(temp,listOfMolecule)){
										molecule.setPosition(temp);
									}
									else{
										//System.out.println("Collision occured while on rail");
									}
									//System.out.println(curpos.getX()+","+curpos.getY()+","+curpos.getZ());
									//molecule.setPosition(curpos);
								}
							}
							else{
								Position newPos = new Position();
								newPos.setX(curpos.getX() + molecule.getStepLengthX()*steparr[(int) (Math.random()*3)]);
								newPos.setY(curpos.getY() + molecule.getStepLengthY()*steparr[(int) (Math.random()*3)]);
								newPos.setZ(curpos.getZ() + molecule.getStepLengthZ()*steparr[(int) (Math.random()*3)]);
								checkBoundary(newPos,molecule.getRadius());
								Position nextPos = checkRailPos(curpos,newPos,molecule);
								if(nextPos!=null){
									//distance = curpos.getDistance(reciever);
									if(molecule.check(nextPos,listOfMolecule)){
										molecule.setPosition(nextPos);
										//System.out.println("Gets On Rail");
									}
									else{
										//System.out.println("Collision happened in random space3");
									}
								}
								else{
									if(molecule.check(newPos,listOfMolecule)){
										molecule.setPosition(newPos);
									}
									else{
										System.out.println("Collision happened in random space4");
									}							
								}
							}
						}
						else{
							Position temp = new Position(curpos.getX() + molecule.getStepLengthX()*steparr[(int) (Math.random()*3)],
									curpos.getY() + molecule.getStepLengthY()*steparr[(int) (Math.random()*3)],
									curpos.getZ() + molecule.getStepLengthZ()*steparr[(int) (Math.random()*3)]);
							checkBoundary(temp,molecule.getRadius());
							if(molecule.check(temp,listOfMolecule)){
								molecule.setPosition(temp);
							}
							else{
								System.out.println("Collision occured while on rail2");
							}
						}
						if(generateOutputFile){
							writers.get(molecule).flush();
						}
					}
				}
				ArrayList<Molecule> temp = new ArrayList<Molecule>();
				for(Molecule molecule:listOfMolecule){
					if(molecule.isReachFlag()){
						temp.add(molecule);
					}
				}
				for(Molecule k:temp){
					listOfMolecule.remove(k);
				}
				if(listOfMolecule.size()==0)
					break;
				else
					System.out.println(listOfMolecule.size());
			}
			writer.close();
			if(generateOutputFile){
				for(Map.Entry<Molecule, FileWriter> entry : writers.entrySet()){
					entry.getValue().close();
				}
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Position checkRailPos(Position curpos, Position newPos, Molecule molecule) {
		// TODO Auto-generated method stub
		double a1 = curpos.getX();
		double b1 = curpos.getY();
		double c1 = curpos.getZ();
		double a2 = newPos.getX();
		double b2 = newPos.getY();
		double c2 = newPos.getZ();
		for(MicroTubule mt: listOfMicroTubule){
			double r = mt.getRadiusMicroTubule();
			Position plusend = mt.getPlusEndCentre();
			Position minusend = mt.getMinusEndCentre();
			double x1 = plusend.getX();
			double y1 = plusend.getY();
			double z1 = plusend.getZ();
			double x2 = minusend.getX();
			double y2 = minusend.getY();
			double z2 = minusend.getZ();
			double l = x2-x1;
			double m = y2-y1;
			double n = z2-z1;
			double a = Math.pow(n*(b2-b1) - m*(c2-c1), 2) +
					Math.pow(l*(c2-c1) - n*(a2-a1), 2) +
					Math.pow(m*(a2-a1) - l*(b2-b1), 2);
			double b = (n*(b2-b1) - m*(c2-c1))*(n*(b1-y1) - m*(c1-z1)) +
					(l*(c2-c1) - n*(a2-a1))*(l*(c1-z1) - n*(a1-x1)) +
					(m*(a2-a1) - l*(b2-b1))*(m*(a1-x1) - l*(b1-y1));

			double c = Math.pow(n*(b1-y1) - m*(c1-z1), 2) +
					Math.pow(l*(c1-z1) - n*(a1-x1), 2) +
					Math.pow(m*(a1-x1) - l*(b1-y1), 2) -
					r*r*(l*l + m*m + n*n);
			double det = -1;
			if((det = b*b - a*c)>= 0){
				double t1 = (Math.sqrt(det) - b)/a ;
				Position pos1 = new Position(a1 + t1*(a2-a1),b1+t1*(b2-b1),c1+t1*(c2-c1));
				double t2 = -(Math.sqrt(det) + b)/a ;
				Position pos2 = new Position(a1 + t2*(a2-a1),b1+t2*(b2-b1),c1+t2*(c2-c1));
				double dist1 = Double.MAX_VALUE;
				double dist2 = Double.MAX_VALUE;
				if(t1>0 && t1<=1 && 
						Math.sqrt(Math.pow(pos1.getDistance(plusend),2) - r*r)<= plusend.getDistance(minusend) &&
						Math.sqrt(Math.pow(pos1.getDistance(minusend),2) - r*r) <= plusend.getDistance(minusend)){
					dist1 = pos1.getDistance(curpos); 
				}
				if(t2>0 && t2<=1 && 
						Math.sqrt(Math.pow(pos2.getDistance(plusend),2) - r*r) <= plusend.getDistance(minusend) &&
						Math.sqrt(Math.pow(pos2.getDistance(minusend),2) - r*r) <= plusend.getDistance(minusend)){
					dist2 = pos2.getDistance(curpos); 
				}
				if(dist1<=dist2 && dist1!=Double.MAX_VALUE){
					molecule.setCurrentMicrotubule(mt);
					return pos1;
				}
				if(dist2<dist1){
					molecule.setCurrentMicrotubule(mt);					
					return pos2;
				}
			}
		}
		return null;
	}

	private boolean hasDRailed() {
		// TODO Auto-generated method stub
		if(Math.random()<=probDrail)
			return true;
		else
			return false;
	}
	private Position getInitPointOnMicrotubule(MicroTubule mt, Position init){
		Position perp = new Position();
		double x1 = mt.getPlusEndCentre().getX();
		double y1 = mt.getPlusEndCentre().getY();
		double z1 = mt.getPlusEndCentre().getZ();
		double x2 = mt.getMinusEndCentre().getX();
		double y2 = mt.getMinusEndCentre().getY();
		double z2 = mt.getMinusEndCentre().getZ();
		double radius = mt.getRadiusMicroTubule();

		double t = ((x1 - init.getX())*(x2-x1) +
				(y1 - init.getY())*(y2-y1) +
				(z1 - init.getX())*(z2-z1)) / ((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) + (z2-z1)*(z2-z1));
		perp.setX(x1 - (x2-x1)*t);
		perp.setY(y1 - (y2-y1)*t);
		perp.setZ(z1 - (z2-z1)*t);
		double perpdist = perp.getDistance(init);
		if(perpdist <= radius &&
				Math.sqrt(Math.pow(init.getDistance(mt.getPlusEndCentre()),2) - perpdist*perpdist)<= mt.getPlusEndCentre().getDistance(mt.getMinusEndCentre()) &&
				Math.sqrt(Math.pow(init.getDistance(mt.getMinusEndCentre()),2) - perpdist*perpdist) <= mt.getPlusEndCentre().getDistance(mt.getMinusEndCentre())){
			Position ret = new Position();
			double dist = radius - perpdist;
			if(perpdist!=0){
				ret.setX(radius*init.getX() - perp.getX()*dist);
				ret.setY(radius*init.getY() - perp.getY()*dist);
				ret.setZ(radius*init.getZ() - perp.getZ()*dist);
				return ret;
			}
			else{
				double a = (x2-x1)/mt.getPlusEndCentre().getDistance(mt.getMinusEndCentre());
				double b = (y2-y1)/mt.getPlusEndCentre().getDistance(mt.getMinusEndCentre());
				double c = (z2-z1)/mt.getPlusEndCentre().getDistance(mt.getMinusEndCentre());
				if(a==0 && b==0){
					ret.setX(perp.getX() + radius);
					ret.setX(perp.getY());
					ret.setX(perp.getZ());
				}
				else{
					ret.setX(perp.getX() + (a*c*radius/Math.sqrt(a*a + b*b)));
					ret.setY(perp.getY() + (b*c*radius/Math.sqrt(a*a + b*b)));
					ret.setZ(perp.getZ() - radius*Math.sqrt(a*a + b*b));
				}
				return ret;
			}
		}
		else
			return null;
	}
	public void paintComponent(Graphics g){

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}