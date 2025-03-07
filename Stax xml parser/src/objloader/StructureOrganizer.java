package objloader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class StructureOrganizer {
	private int numberofAtoms;

	public static Vector<float[]> AtomsData = new Vector<float[]>();
	//numbers per array in order: ID, type, number of bonds currently on atom, X location,
	//Y location, Z location 
	public static Vector<float[]> BondsData = new Vector<float[]>();
	//numbers per array in order: Begins, Ends, length, X beginning,
	//Y beginning, Z beginning, X end, Y end, Z end, order, is placed 
	private boolean complete = false;
	
	
	public StructureOrganizer(parser p){
		//called by JOGLRenderer to create structure for atoms

		//put all data in AtomsData and BondsData
		int ID = 0;
		int BondID = 0;

		AtomsData = convertNode(p.return_nodes());
		BondsData = convertBond(p.return_bonds()); 
		System.out.println(BondsData.get(0)[0]+ " "+ BondsData.get(0)[1]);
		System.out.println(BondsData.get(1)[0]+ " "+ BondsData.get(1)[1]);
		System.out.println(AtomsData.get(0)[0]);
		System.out.println(AtomsData.get(1)[0]);
		System.out.println(AtomsData.get(2)[0]);
		
		int Count = 0;
		while(complete == false){ //while there is still unfilled data 

			int Unfinished = 0;
			System.out.println("Count is: " + Count);
			if(Count == 0){ //if this is the first atom 
				Place_Atom(ID, BondID); //place an atom
				getBondGeometry((int) AtomsData.get(ID)[0], (int) AtomsData.get(ID)[2], ID); 
				//get the bond geometry for that atom
				System.out.println("Bond values are: "+ BondsData.get(0)[6] + " "
							+ BondsData.get(1)[6]);
				Unfinished++;
			}else{
				int j = 0;
				while(j < AtomsData.size()){ //for all atoms
					if(AtomsData.get(j)[3] != 0 || AtomsData.get(j)[4] != 0 
					|| AtomsData.get(j)[5] != 0){ //if the atom isn't at the origin
						getBondGeometry( (int) AtomsData.get(j)[0],
						(int) AtomsData.get(j)[2], j); // get its bond geometry
						Unfinished = Unfinished + 1; 
					}
					j = j + 1;
				}
			complete = true; 
			for(int i = 0; i < BondsData.size(); i++){
				if(BondsData.get(i)[10] == 0){
					complete = false; 
				}
			}
			System.out.println(BondsData.get(1)[6]);
			for(int i = 0; i < BondsData.size(); i++){ //for all bonds
				if(BondsData.get(i)[6] != 0 || BondsData.get(i)[7] != 0
						|| BondsData.get(i)[8] != 0){// if the bond doesn't end on the 
					//origin (ie it has been placed)
						System.out.println("I is "+ i);
						Place_Atom((int)BondsData.get(i)[1], i);
					//place the correct atom at the end of it.
				}
			}
			
			}
			Count = Count + 1;
		
		}
	}
	private Vector<float[]> convertBond(HashMap<Integer, Bond> return_bonds) {
		Vector<float[]> Bond_data = new Vector<float[]>();
		int count = 0; 
		for (Integer key : return_bonds.keySet()) {
			
		    Bond_data.add(new float[11]);
		    Bond_data.get(count)[0] = return_bonds.get(key).getB();
		    Bond_data.get(count)[1] = return_bonds.get(key).getE();
		    Bond_data.get(count)[2] = 1; //get library for this 
		    Bond_data.get(count)[3] = 0; 
		    Bond_data.get(count)[4] = 0;
		    Bond_data.get(count)[5] = 0;
		    Bond_data.get(count)[6] = 0;
		    Bond_data.get(count)[7] = 0;
		    Bond_data.get(count)[8] = 0;
		    Bond_data.get(count)[9] = return_bonds.get(key).getOrder();
		    Bond_data.get(count)[10] = 0;
		    count++;
		}
		return Bond_data;
	}
	
	private Vector<float[]> convertNode(HashMap<Integer, Node> return_nodes) {
		Vector<float[]> Node_data = new Vector<float[]>();
		int count = 0; 
		for (Integer key : return_nodes.keySet()) {  //key is the index of the hash map
		    Node_data.add(new float[10]);
		    Node_data.get(count)[0] = return_nodes.get(key).getID();
		    Node_data.get(count)[1] = return_nodes.get(key).getElement();
		    Node_data.get(count)[2] = 0; 
		    Node_data.get(count)[3] = 0; 
		    Node_data.get(count)[4] = 0;
		    Node_data.get(count)[5] = 0;
		    count++;
		}
		return Node_data;
	}
	public void Place_Atom(int ID, int BondID){
		//places an atom

		//if there is nothing else, place at origin 
		for(int i = 0; i < AtomsData.size(); i++){
			if(AtomsData.get(i)[0] == ID){
				ID = i; 
			}
		}
		System.out.println("Entered Atom Placement");
		if(numberofAtoms == 0){
			AtomsData.get(ID)[3] = 10;
			AtomsData.get(ID)[4] = 10;
			AtomsData.get(ID)[5] = 10;
			numberofAtoms = numberofAtoms +1;
		}else{

			//if there is other stuff, place this atom at the end of the bond
			//it is associated with 
			
			AtomsData.get(ID)[3] = BondsData.get(BondID)[6];
			AtomsData.get(ID)[4] = BondsData.get(BondID)[7];
			AtomsData.get(ID)[5] = BondsData.get(BondID)[8];
			numberofAtoms = numberofAtoms +1;
		}
		System.out.println("The just placed atom is: " + AtomsData.get(ID)[3]);
	}

	
	public int[] CalculateHybridization(int IDnumber){
		LinkedList<int[]> LL = new LinkedList<int[]>();
		
		for (int i=0; i < BondsData.size(); i++){
			
		
			if(BondsData.get(i)[0] == IDnumber || BondsData.get(i)[1] == IDnumber){
				int [] d= new int[1];
				d[0]=i;
				LL.add(d);
				System.out.println("D is: " + d[0]);
			}
		}
		int [] data = new int[LL.size()];
		for (int i = 0; i < LL.size(); i ++){
			data[i]= LL.get(i)[0];
		}
 
		return data;
	}
	
	
	
	public void getBondGeometry(int ID, int numBonds, int Key){
		
		float dx = 0;
		float dy = 0;
		float dz = 0;
		//get the array of bonds connected to to the atom ID 
		int[] bond = CalculateHybridization(ID);
	
		
		//get the value in the vector corresponding to the key
		for(int k = 0; k < AtomsData.size(); k++){
			if(AtomsData.get(k)[0] == ID){
				ID = k; 
			}
		}

		if(bond == null){
			return;
		}
		//the number of bonds currently attached to the atom 
		if(numBonds == bond.length){
			return; 
		}
		
		for(int i = 0; i < bond.length; i++){ //set the start point of each bond
			BondsData.get(bond[i])[3] = AtomsData.get(ID)[3]; 
			BondsData.get(bond[i])[4] = AtomsData.get(ID)[4]; 
			BondsData.get(bond[i])[5] = AtomsData.get(ID)[5]; 
		}
		
		if(numBonds == 0){ //if there are currently no bonds on the screen
			//set the first bond pointing in the X direction.

			if(bond.length == 1){
				BondsData.get(bond[0])[6] = BondsData.get(bond[0])[3] +
						BondsData.get(bond[0])[2];
				BondsData.get(bond[0])[7] = BondsData.get(bond[0])[4];
				BondsData.get(bond[0])[8] = BondsData.get(bond[0])[5];
				AtomsData.get(Key)[2] ++; 
				bond[0] = -1;
			}
			if(bond.length == 2){

				BondsData.get(bond[0])[6] = BondsData.get(bond[0])[3] +
						BondsData.get(bond[0])[2];
				BondsData.get(bond[0])[7] = BondsData.get(bond[0])[4];
				BondsData.get(bond[0])[8] = BondsData.get(bond[0])[5];
				BondsData.get(bond[1])[6] = BondsData.get(bond[1])[3] -
						BondsData.get(bond[1])[2];

				BondsData.get(bond[1])[7] = BondsData.get(bond[1])[4];
				BondsData.get(bond[1])[8] = BondsData.get(bond[1])[5];
			
				AtomsData.get(Key)[2] ++; 
				System.out.println(BondsData.get(0)[3]);
				System.out.println(BondsData.get(1)[3]);	
				System.out.println(BondsData.get(0)[6]);
				System.out.println(BondsData.get(1)[6]);
				//bonds in correct placement after first iteration
				
				//indicates this bond has been placed 
				for(int i = 0; i < bond.length; i++){
					BondsData.get(bond[i])[10] = 1;
				}
				
			
			
		}else{ 

			for(int i = 0; i < bond.length; i++){
				if(bond[i] != -1){
					if(BondsData.get(bond[i])[6] != 0 && BondsData.get(bond[i])[7] != 0
							&& BondsData.get(bond[i])[8] != 0){
						dx = -BondsData.get(bond[i])[6] + AtomsData.get(ID)[3];
						dy = -BondsData.get(bond[i])[7] + AtomsData.get(ID)[4];
						dz = -BondsData.get(bond[i])[8] + AtomsData.get(ID)[5];
						bond[i] = -1;
					}
				}
			}
		
		
		if(bond.length == 1){
			AtomsData.get(Key)[2] ++; 
			return;
			
		}
		if(bond.length == 2){ //set the un-established bond pointing in the other direction
			/*System.out.println("entered display loop");
			for(int i = 0; i < bond.length; i++){
				if(bond[i] != -1){
					BondsData.get(bond[i])[6] = dx + AtomsData.get(ID)[3]; 
					BondsData.get(bond[i])[7] = dy + AtomsData.get(ID)[4];
					BondsData.get(bond[i])[8] = dz + AtomsData.get(ID)[5];
				}
			}
			AtomsData.get(Key)[2] ++; 
			*/
			return; 
		}
		if(bond.length == 3){
			AtomsData.get(Key)[2] ++; 
			return;
		}
		if(bond.length == 4){
			AtomsData.get(Key)[2] ++; 
			return;
		}
		if(bond.length == 5){
			AtomsData.get(Key)[2] ++; 
			return;
		}
		if(bond.length == 6){
			AtomsData.get(Key)[2] ++; 
			return;
		}
	}}
	
	}}
