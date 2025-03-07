
import java.util.Vector;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * This is the XML parser which extracts useful information from a cdxml file
 * generated by ChemDraw. This information will be passed onto the display
 * methods to be turned into a 3D structure.
 * 
 * @author juliegeng
 * 
 */



public class parser {
	static Vector<float[]> Atoms = new Vector<float[]>();
	static Vector<float[]> Bonds = new Vector<float[]>();
	
	public static void main(String[] args) throws XMLStreamException, Exception {
	
		
		Node currNode = null; // Constructs a current node
		Bond currBond = null; // Constructs a current bond
		String Text = null; 
		String tagContent = null; // TagContent is any attribute associated with
									// a bond or node

		XMLInputFactory factory = XMLInputFactory.newInstance();
		// "The class javax.xml.stream.XMLInputFactory is a root component of
		// the Java StAX API. From this class you can create both an
		// XMLStreamREader
		// and an XMLEventReader." In other words, this defines the StAX parser.

		FileInputStream fis = new FileInputStream("xml/Terephthalic acid.cdxml");
		// The InputStream reads a cdxml file.
		XMLStreamReader reader = factory.createXMLStreamReader(fis);
		// This iterates through the XML file using next().

		while (reader.hasNext()) { // The XMLStreamReader is iterating through
									// the file.
			int event = reader.next();

			switch (event) {
			case XMLStreamConstants.START_ELEMENT: // Indicates an event is a
													// start element
				if ("n".equals(reader.getLocalName())) { // if "n" is detected
					currNode = new Node(); // Create a node object
					currNode.id = reader.getAttributeValue(null, "id");
					// Access the value of "id" of this node
					// id: A unique identifier for an object, used when other
					// objects refer to it.
					currNode.Element = reader
							.getAttributeValue(null, "Element");
					// Access the value of "Element" of this node
					// Element: The atomic number of the atom representing this
					// node.
					// If not specified, the atomic number is 6, corresponding
					// to carbon
					// (my personal favorite, FYI)! 
				}

				if ("b".equals(reader.getLocalName())) { // Analogous to the "n"
															// scenario
					currBond = new Bond();
					currBond.B = reader.getAttributeValue(null, "B");
					currBond.E = reader.getAttributeValue(null, "E");
					currBond.Order = reader.getAttributeValue(null, "Order");
					// Order: The order of a bond object (single/double/triple)
				}
				break;
				
			 case XMLStreamConstants.CHARACTERS:
				 tagContent = reader.getText().trim();
				 break;

			case XMLStreamConstants.END_ELEMENT: // indicates the end of an
													// element
				switch (reader.getLocalName()) {
				case "Element":
					currNode.Element = tagContent;// Element is a identified as
													// a tag content.
					break;
				case "n":
					float[] currAtom = new float [6];
					float f_id = Float.valueOf(currNode.id.trim()).floatValue();
					float f_element = Float.valueOf(currNode.Element.trim()).floatValue();
					currAtom[0]= f_id;
					currAtom[1]= f_element;
					currAtom[2]=0; //number of bonds
					currAtom[3]=0; //x beginning
					currAtom[4]=0; //y beginning
					currAtom[5]=0; //z beginning
					Atoms.add(currAtom); 
					break;
				
				case "b":
					float[] newBond = new float [9];
					float f_id_bond_begin = Float.valueOf(currBond.B.trim()).floatValue();
					float f_id_bond_end = Float.valueOf(currBond.E.trim()).floatValue();
					newBond[0]= f_id_bond_begin ;
					newBond[1]= f_id_bond_end ;  //end
				    newBond[2]=1;   //length
					newBond[3]=0;   //x beginning
					newBond[4]=0;  //y beginning 
					newBond[5]=0; //z beginning
					newBond[6]=0; //z end
					newBond[7]=0; //y end
					newBond[8]=0; //z end
			
					Bonds.add(newBond); 
					break;
					
				case "Order":
					currBond.Order = tagContent; // Order is a identified as a
													// tag content.
					break;
					
				
				}
				break;

	}
		}
	}
	

        public Vector<float[]> returnAtoms(){
                return Atoms;
        }
        public Vector<float[]> returnBonds(){
                return Bonds;
        }
        
	static class Bond {
		String id;
		String Order;
		String B;
		String E; 

		public String toString() {
			return id + " " + Order;
		}
	}

	static class Node {
		String id;
		String p;
		String Z;
		String Element;

		public String toString() {
			return id + " " + Element;
		}
	}

}
