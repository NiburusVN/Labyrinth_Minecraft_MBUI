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

		// Log de l'image de fond
		if (image1 == null) {
			throw new IllegalArgumentException("Background image is null!");
		}
		System.out.println("Background image dimensions: " + image1.getWidth() + "x" + image1.getHeight());

		BufferedImage mergedImage = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = mergedImage.createGraphics();
		g2d.drawImage(image1, 0, 0, null);

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		for (int i = 0; i < foregroundPaths.length; i++) {
			String[] data = foregroundPaths[i];
			if (data != null) {
				// Log des informations de l'image foreground
				System.out.println("Processing foreground image [" + i + "]:");
				System.out.println("  Path: " + data[0]);

				// Vérification de l'existence du fichier
				File file = new File(data[0]);
				if (!file.exists()) {
					System.err.println("  ERROR: File does not exist at path: " + file.getAbsolutePath());
					continue;
				}

				int x = Integer.parseInt(data[1]);
				int y = Integer.parseInt(data[2]);
				int width = Integer.parseInt(data[3]);
				int height = Integer.parseInt(data[4]);
				System.out.println("  Position: (" + x + ", " + y + ")");
				System.out.println("  Dimensions: " + width + "x" + height);

				// Lecture de l'image foreground
				BufferedImage foregroundImage;
				try {
					foregroundImage = ImageIO.read(file);
					if (foregroundImage == null) {
						System.err.println("  ERROR: Failed to load image (null returned by ImageIO.read)");
						continue;
					}
					System.out.println("  Foreground image loaded successfully. Original dimensions: "
							+ foregroundImage.getWidth() + "x" + foregroundImage.getHeight());
				} catch (IOException e) {
					System.err.println("  ERROR: Failed to read image file: " + e.getMessage());
					continue;
				}

				// Redimensionnement de l'image
				Image resizedImage = foregroundImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				System.out.println("  Foreground image resized successfully.");

				// Dessiner l'image foreground redimensionnée
				g2d.drawImage(resizedImage, x, y, null);
				System.out.println("  Foreground image drawn at position (" + x + ", " + y + ").");
			} else {
				System.out.println("Skipping null foreground image data at index [" + i + "].");
			}
		}

		g2d.dispose();
		System.out.println("Merged image created successfully.");
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
			int width = Integer.parseInt(data[3]);
			int height = Integer.parseInt(data[4]);

			// Skip the image to exclude
			if (imagePath.equals(imageToExclude)) {
				continue;
			}

			BufferedImage foregroundImage = ImageIO.read(new File(imagePath));
			Image resizedImage = foregroundImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			g2d.drawImage(resizedImage, x, y, null);
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
