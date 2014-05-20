package simulatepath;

import java.util.ArrayList;
import java.util.Random;

public class RandomDistribute {
	public static void distribute(double medX, double medY, double medZ, ArrayList<Molecule> list,double r){
		int size = list.size();
		boolean flag;
		int x=(int) (-medX/2+r),y=(int) (-medY/2+r),z=(int) (-medZ/2+r);
		Random rand = new Random(System.currentTimeMillis());
		
		for(int i=0;i<size;i++){
			flag=false;
			while(!flag){
				double varx = (int)(Math.random()*2)==0?rand.nextInt(Math.abs(x))+Math.random(): -rand.nextInt(Math.abs(x))-Math.random();
				double vary = (int)(Math.random()*2)==0?rand.nextInt(Math.abs(y))+Math.random(): -rand.nextInt(Math.abs(y))-Math.random();
				double varz = (int)(Math.random()*2)==0?rand.nextInt(Math.abs(z))+Math.random(): -rand.nextInt(Math.abs(z))-Math.random();
				list.get(i).getPosition().setX(varx);
				list.get(i).getPosition().setY(vary);
				list.get(i).getPosition().setZ(varz);
				int j=0;
				for(j=0;j<i;j++){
					if(list.get(i).getPosition().getDistance(list.get(j).getPosition())<2*r)
						break;
				}
				if(j==i)
					flag = true;
			}
		}
	}
}
