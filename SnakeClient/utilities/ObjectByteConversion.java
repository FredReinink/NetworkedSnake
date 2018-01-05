package utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectByteConversion {

	// toByteArray and toObject are taken from: http://tinyurl.com/69h8l7x
	public static byte[] toBytes(Object obj) throws IOException {
	  	byte[] bytes = null;
      	ByteArrayOutputStream byteArrayOut = null;
      	ObjectOutputStream objectOut = null;
      	try {
    	  	byteArrayOut = new ByteArrayOutputStream();
          	objectOut = new ObjectOutputStream(byteArrayOut);
          	objectOut.writeObject(obj);
          	objectOut.flush();
          	bytes = byteArrayOut.toByteArray();
      	} finally {
    	  	//clean up
    	  	if (objectOut != null) {
        	  	objectOut.close();
          	}
          	if (byteArrayOut != null) {
        	  	byteArrayOut.close();
          	}
      	}
      	return bytes;
  	}

	 public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
	     Object obj = null;
	     ByteArrayInputStream byteArrayIn = null;
	     ObjectInputStream objectIn = null;
	     try {
	         byteArrayIn = new ByteArrayInputStream(bytes);
	         objectIn = new ObjectInputStream(byteArrayIn);
	         obj = objectIn.readObject();
	     } finally {
	         if (byteArrayIn != null) {
	             byteArrayIn.close();
	         }
	         if (objectIn != null) {
	             objectIn.close();
	         }
	     }
	     return obj;
	 }
}