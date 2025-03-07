package objloader;

import java.io.*;

import javax.media.opengl.GL;

import simplerjogl.*;

/**
 * Demonstrate the use of an OBJ Loader to import Wavefront OBJ files into
 * SimplerJOGL.
 * 
 * @author <a href="mailto:seth@battis.net">Seth Battis</a>
 * 
 */
public class JOGLRenderer extends Renderer {


	

	private int degreesOfRotation;
	private Light basicLight;

	public void init() {
		/*
		 * load the OBJ file from the model directory as a model. Note that,
		 * since we are dealing with file I/O, IOExceptions may be thrown so we
		 * either need to throw them ourselves or catch them.
		 */
		/*
		try {
			//suzanne = new ObjLoader(gl).load("C:\\Users\\Owner\\Documents\\Spacer.obj");
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		/*
		for(int i = 0; i < 3;i++){
		System.out.println("Atom x: " + StructureOrganizer.AtomsData.get(i)[3]);
		System.out.println("Atom y: " + StructureOrganizer.AtomsData.get(i)[4]);
		System.out.println("Atom z: " + StructureOrganizer.AtomsData.get(i)[5]);
		}
		for(int i = 0; i < 2; i++){
			System.out.println(StructureOrganizer.BondsData.get(i)[3] + " "+ 
					StructureOrganizer.BondsData.get(i)[6]);
		}
		System.out.println(StructureOrganizer.BondsData.get(0)[6]);
		System.out.println(StructureOrganizer.BondsData.get(1)[6]);

		
		degreesOfRotation = 0;
		//basicLight = new Light(gl);
		//basicLight.enable();
	}
	*/
	public void display() {
		glu.gluLookAt(10, 10, 20, 10, 10, 10, 0, 1, 0);
		gl.glRotatef(degreesOfRotation++, 1, 1, 0);
		//place(0,0,0,0);
		glut.glutWireSphere(0.2,10,10);
		//draw atoms
		/*
		for(int i = 0; i < StructureOrganizer.AtomsData.size(); i++){
			//System.out.println(StructureOrganizer.AtomsData.get(i)[3]);
			gl.glPushMatrix();{
			gl.glTranslated(StructureOrganizer.AtomsData.get(i)[3], 
					StructureOrganizer.AtomsData.get(i)[4]
					, StructureOrganizer.AtomsData.get(i)[5]);
			glut.glutWireSphere(0.2,10,10);
			}
			gl.glPopMatrix();
			*/ 

			
			
		}
		/*
		//draw bonds
		for(int i = 0; i < StructureOrganizer.BondsData.size(); i++){
			   gl.glBegin (GL.GL_LINES);
			   gl.glVertex3f (StructureOrganizer.BondsData.get(i)[3], 
					   StructureOrganizer.BondsData.get(i)[4],
					   StructureOrganizer.BondsData.get(i)[5]);
			   gl.glVertex3f (StructureOrganizer.BondsData.get(i)[6], 
					   StructureOrganizer.BondsData.get(i)[7],
					   StructureOrganizer.BondsData.get(i)[8]);
			
			   gl.glEnd();
		}
		*/ 
	
	
	//public void place(int atomNumber,float dx,float dy,float dz){
	//	suzanne.draw();
	//}
}}
		