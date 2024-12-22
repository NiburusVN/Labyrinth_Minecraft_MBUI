package helpers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Set of static functions allowing to manipulate images
 *
 * @author Adrien Krähenbühl
 */
public class ImageHelper {
	/**
	 *  Generate a new image from a background image and a foreground image
	 *
	 * @param image1 is the BufferedImage of the background image
	 * @param foregroundPaths is the list of path of the other images
	 * @return an image combining the foreground image over the background image
	 */
	public static BufferedImage merge(BufferedImage image1, String[][] foregroundPaths) throws IOException {

		BufferedImage mergedImage = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = mergedImage.createGraphics();
		g2d.drawImage(image1, 0, 0, null);

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		for (String[] data : foregroundPaths) {
			String imagePath = data[0];
			int x = Integer.parseInt(data[1]);
			int y = Integer.parseInt(data[2]);
			int width = Integer.parseInt(data[3]);
			int height = Integer.parseInt(data[4]);

			BufferedImage foregroundImage = ImageIO.read(new File(imagePath));
			// Redimensionner et dessiner l'image
			Image resizedImage = foregroundImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			g2d.drawImage(resizedImage, x, y, null);
		}

		g2d.dispose();
		return mergedImage;
	}

	/**
	 *  Generate a new image from a background image and foreground images exluding a specific image
	 *
	 * @param image1 is the BufferedImage of the background image
	 * @param foregroundPaths is the list of path of the other images
	 * @return an image combining the foreground image over the background image
	 */
	public static BufferedImage mergeExcluding(BufferedImage image1, String[][] foregroundPaths, String imageToExclude) throws IOException {

		BufferedImage mergedImage = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = mergedImage.createGraphics();
		g2d.drawImage(image1, 0, 0, null);

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		for (String[] data : foregroundPaths) {
			if(data == null){
				continue;
			}

			String imagePath = data[0];
			int x = Integer.parseInt(data[1]);
			int y = Integer.parseInt(data[2]);

			// Skip the image to exclude
			if (imagePath.equals(imageToExclude)) {
				continue;
			}

			BufferedImage foregroundImage = ImageIO.read(new File(imagePath));
			g2d.drawImage(foregroundImage, x, y, null);
		}

		g2d.dispose();
		return mergedImage;
	}

	/**
	 *  Rotate an original image from the center by a defined angle.
	 *  The original image MUST HAVE the same width and height.
	 *
	 * @param original is the input image to rotate
	 * @param angle is the angle of rotation in radian
	 * @return a new image representing the original image rotated of angle radian from its center.
	 */
	public static BufferedImage rotate( final BufferedImage original, double angle ) throws IllegalArgumentException {
		if ( original.getWidth() != original.getHeight() )
			throw new IllegalArgumentException("Original image must have same width and height.");
		BufferedImage rotated = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
		Graphics2D graphic = rotated.createGraphics();
		graphic.rotate(angle, original.getWidth()/2., original.getHeight()/2.);
		graphic.drawRenderedImage(original, null);
		graphic.dispose();
		return rotated;
	}

	/**
	 *  Rotate an original image from the center by 90 degrees in clockwise
	 *
	 * @param original is the input image to rotate
	 * @return a new image representing the original image rotated by 90 degrees from its center.
	 */
	public static BufferedImage rotateClockwise( final BufferedImage original ) throws IllegalArgumentException {
		return rotate( original, 0.5*Math.PI );
	}

	/**
	 *  Rotate an original image from the center by 90 degrees in counterclockwise
	 *
	 * @param original is the input image to rotate
	 * @return a new image representing the original image rotated by 90 degrees from its center.
	 */
	public static BufferedImage rotateCounterClockwise( final BufferedImage original ) throws IllegalArgumentException {
		return rotate( original, 1.5*Math.PI );
	}
}
