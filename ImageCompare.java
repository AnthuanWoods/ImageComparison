import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.image.DataBuffer;
import java.io.IOException;

public class ImageCompare {
	public static void main(String[] arguments) throws IOException{
		//creates the 2 files for the program to run
		File A = new File("C:\\Users\\anthu\\Documents\\Java\\ImageCompare\\images\\football.jpg");
		File B = new File("C:\\Users\\anthu\\Documents\\Java\\ImageCompare\\images\\SoccerBall.jpg");	
		ImageCompare a = new ImageCompare();
		double percent = (double)a.compareImage(A,B);
		System.out.println(percent);
	}

	public float compareImage(File fileA, File fileB) throws IOException{
		//variable
		float percentage = 0;
		try {
			// take buffer data from both image files //
			BufferedImage biA = ImageIO.read(fileA);
			DataBuffer dbA = biA.getData().getDataBuffer();
			int sizeA = dbA.getSize();
			BufferedImage biB = ImageIO.read(fileB);
			DataBuffer dbB = biB.getData().getDataBuffer();
			int sizeB = dbB.getSize();
			int count = 0;
			//if A is larger than B
			if(sizeA > sizeB){
				//scale A down to B size and get buffer data
				Image tempimg = biA.getScaledInstance(biB.getWidth(),biB.getHeight(),Image.SCALE_SMOOTH);
				BufferedImage newImage = new BufferedImage(biB.getWidth(),biB.getHeight(),biB.getType());
				newImage.getGraphics().drawImage(tempimg,0,0,null);
				DataBuffer tempdB = newImage.getData().getDataBuffer();
				//iterate through the image data for matching data
				for (int i = 0; i < sizeB; i++) {	
					if (dbA.getElem(i) == tempdB.getElem(i)) {
						count = count + 1;
					}
				}
				percentage = (count * 100) / sizeA;
			}
			//if B is larger than A
			else if(sizeB > sizeA){
				Image tempimg = biA.getScaledInstance(biA.getWidth(),biA.getHeight(),Image.SCALE_SMOOTH);
				BufferedImage newImage = new BufferedImage(biA.getWidth(),biA.getHeight(),biA.getType());
				newImage.getGraphics().drawImage(tempimg,0,0,null);
				DataBuffer tempdB = newImage.getData().getDataBuffer();
				for (int i = 0; i < sizeA; i++) {
					if (dbA.getElem(i) == tempdB.getElem(i)) {
						count = count + 1;
					}
				}
				percentage = (count * 100) / sizeA;
			}
			//if A and B are the same size
			if (sizeA == sizeB) {
				for (int i = 0; i < sizeA; i++) {
					if (dbA.getElem(i) == dbB.getElem(i)) {
						count = count + 1;
					}
				}
				percentage = (count * 100) / sizeA;
			}
		} catch (Exception e) {
			System.out.println("Failed to compare image files ...");
		}
		return percentage;
	}

}