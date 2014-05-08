package simulatepath;

import java.util.ArrayList;

public class Distribution {
	public static void distribute(double medX, double medY, double medZ, ArrayList<Molecule> list,double r){
		int size = list.size();
		int index=0;
		int m=1;
		double x=-medX/2+r,y=-medY/2+r,z=-medZ/2+r,xdisp=0,ydisp=0,zdisp=0;
		int first = (int) Math.cbrt(size);
		while(size>0){
			int d = (int) Math.cbrt(size);
			if(size>2){
				int numx=0;
				int numy=0;
				int numz=0;
				for(int i=0;i<d;i++){
					for(int j=0;j<d;j++){
						for(int k=0;k<d;k++){
							if(numz>d){
							Position p = list.get(index).getPosition();
							p.setX(x + xdisp);
							p.setY(y + ydisp);
							p.setZ(z + zdisp);
							for(int a=0;a!=m;a++){
								zdisp = zdisp + medZ/(d*m);
							}
							index++;
							numz++;
							}
						}
						if(numy>d){
						for(int a=0;a!=m;a++){
							ydisp = ydisp + medY/(d*m);
						}
						numy++;
						}
					}
					for(int a=0;a!=m;a++){
						xdisp = xdisp + medX/(d*m);
					}
				}
				while(d>first*2){
					m++;
					xdisp = medX/(Math.pow(2, m-1)*d);
					ydisp = medY/(Math.pow(2, m-1)*d);
					zdisp = medZ/(Math.pow(2, m-1)*d);
				}
			}
			else{

			}
			size = size - d*d*d;
		}
	}
}
