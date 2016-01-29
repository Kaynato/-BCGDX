package net.suizinshu.file;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.suizinshu.external.Central;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Decodes a file.
 * @author Zicheng Gao
 */
public class Decoder {

	/**
	 * Takes file of path inPath and decrypts it into outFile
	 * @param inPath	input file path
	 * @param outputFile	output file
	 * @throws IOException when streams encounter an error
	 */
	public static void decrypt(FileHandle inputFile, FileHandle outputFile) throws IOException {

		// Perform the operation
		if (inputFile.exists()) {
			
			// Initialize input
			BufferedInputStream input = inputFile.read(1);
			
			// Initialize output
			OutputStream output = outputFile.write(false);

			// Get the key
			int key = key(inputFile.name());

			// Decode and write using key
			decode(input, output, key);

			// Close
			input.close();
			output.close();
		}
		else throw new IOException("Input file does not exist!");

	}
	
	/**
	 * Calls decrypt() and outputs to a temporary file which path is returned here
	 * @param inPath	Path to file which should be decrypted
	 * @param dir		Which directory within temp to output this file (please include \\)
	 * @return			The output file
	 */
	protected static FileHandle tempdecrypt(FileHandle inPath, String dir) {

		// Initialize output file path (redundant)
		FileHandle output = Gdx.files.absolute(Central.TMPDIR + dir + '/' + inPath.name());
		try {
			// Decode into output
			decrypt(inPath, output);
		} catch (IOException e) {
			System.err.println("File decoding encountered an error!");
			e.printStackTrace();
		}
		return output;
	}
	
	/**
	 * Obtain key from a string - feel free to change.
	 * @param name	string to get key from
	 * @return		key as int
	 */
	private static int key(String name) {
		int key = 1;
		for (int i = 0; i < name.length(); i++)
			key += name.charAt(i);
		return key;
	}
	
	/**
	 * Decodes the input stream into the output stream according to key.
	 * @param input
	 * @param output
	 * @param key
	 * @throws IOException
	 */
	private static void decode(InputStream input, OutputStream output, int key) throws IOException {
		int origKey = key;
		boolean keypass;
		for (int data = input.read(); data != -1; data = input.read()) {
			keypass = key % 2 == 1;
			if (keypass)
				output.write(data+1);					
			else
				output.write((byte)(((int)(~data)) - 1));
			key = (key / 2 == 0) ? (origKey) : (key / 2);
		}
	}
	
	public static void main(String[] args) {
		String maximum = "assets";
//		System.out.println(Integer.toHexString(key(maximum)));
		byte[] arr = maximum.getBytes();
		int key = key(maximum);
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] *= -key;
		}
		
		StringBuilder sb = new StringBuilder();
	    for (byte b : arr) {
	        sb.append(String.format("%02X ", b));
	    }
	    System.out.println(sb.toString());
	    System.out.println(arr.length + " bytes long.");
	    
		// That is, allocate 4 bytes for each file found.
	}

}