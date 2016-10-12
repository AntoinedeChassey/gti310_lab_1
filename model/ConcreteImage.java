package model;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import view.ImagePanel;

/**
 * [... comments ...]
 * 
 * BMP non-compress� de 24 bits par pixels only.
 * 
 * @author Manuel Nero, Antoine de Chassey
 */

public class ConcreteImage extends Image {
	BufferedImage _image = null; // The image buffer containing color pixels
									// information.

	private String _bfType = "";
	private int _bfSize = 0;
	private int _biSize = 0;
	private int _biWidth = 0;
	private int _biHeight = 0;
	private int _biPlanes = 0;
	private int _biBitcount = 0;
	private int _biCompression = 0;
	private int _biSizeimage = 0;
	private int _biXPelsPerMeter = 0;
	private int _biYPelsPerMeter = 0;
	private int _biClrUsed = 0;
	private int _biClrImportant = 0;

	public ConcreteImage(File file) {
		super._file = file;
		DataInputStream input = null;

		try {
			input = new DataInputStream(new FileInputStream(file));
			readImageHeader(input);
			if (checkBMPformat() && _biBitcount == 24) {
				System.out.println("File is a valid BMP format.");
				readImageColors(input);
			}
			input.close();
		} catch (Exception e) {
			_image = null; // Reset changes if exception.
			System.err.println("File is not a BMP format.");
		}
	};

	private boolean checkBMPformat() {
		boolean isBMP = false;
		List<String> formats = new ArrayList<>();
		formats.add("BM");
		formats.add("BA");
		formats.add("CI");
		formats.add("CP");
		formats.add("IC");
		formats.add("PT");
		for (String format : formats) {
			if (_bfType.equals(format))
				isBMP = true;
		}
		return isBMP;
	}

	private void readImageColors(DataInputStream input) throws IOException {
		_image = new BufferedImage(this._biWidth, this._biHeight, BufferedImage.TYPE_INT_RGB);

		int padding = this._biWidth % 4;

		// Reading upside-down : from bottom-left to right, then going up one
		// row, repeat.
		for (int posY = _biHeight - 1; posY >= 0; --posY) {
			for (int posX = 0; posX < _biWidth; ++posX) {
				int rgbValue = readRGBValue(input);
				_image.setRGB(posX, posY, rgbValue);
			}

			input.skipBytes(padding);
		}
	}

	private void readImageHeader(DataInputStream input) throws IOException {
		// For testing purpose only.
		// String s0 = Integer.toBinaryString(input.read());
		// String s1 = Integer.toBinaryString((input.read() & MASK));
		// String s2 = Integer.toBinaryString(((input.read()) << 8));
		// String s3 = Integer.toBinaryString(((input.read() & MASK) << 8));

		// _bfType = "" + (char)byteBuffer[0] + (char)byteBuffer[1];
		_bfType = "" + (char) input.read() + (char) input.read();

		// _bfSize = (byteBuffer[2] & MASK) | ((byteBuffer[3] & MASK) << 8) |
		// ((byteBuffer[4] & MASK) << 16) | ((byteBuffer[5] & MASK) << 24);
		_bfSize = readDWordValue(input);
		// Jump over reserved space and bdOffBits.
		input.skipBytes(8);

		// Start to read bitmap info header
		_biSize = readDWordValue(input);
		_biWidth = readDWordValue(input);
		_biHeight = readDWordValue(input);
		_biPlanes = readWordValue(input);
		_biBitcount = readWordValue(input);
		_biCompression = readDWordValue(input);
		_biSizeimage = readDWordValue(input);
		_biXPelsPerMeter = readDWordValue(input);
		_biYPelsPerMeter = readDWordValue(input);
		_biClrUsed = readDWordValue(input);
		_biClrImportant = readDWordValue(input);
	}

	private int readRGBValue(DataInputStream input) throws IOException {
		// Reading blue, green and red in that order.

		// For testing purpose only.
		// String blueNoMask = Integer.toBinaryString(input.readByte());
		// String blueWithMask = Integer.toBinaryString( (input.readByte() &
		// 0xff));
		// String greenWithMaskNoShift =
		// Integer.toBinaryString((input.readByte() & 0xff));
		// String greenWithMaskShift = Integer.toBinaryString((input.readByte()
		// & 0xff) << 8);

		int b = (input.readByte() & 0xff);
		int g = (input.readByte() & 0xff) << 8;
		int r = (input.readByte() & 0xff) << 16;

		// String rg = Integer.toBinaryString(r | g);
		// String rgb = Integer.toBinaryString((r | g ) | b);

		return (r | g) | b;
	}

	private int readDWordValue(DataInputStream input) throws IOException {
		return (input.readByte() & 0xff) | ((input.readByte() & 0xff) << 8) | ((input.readByte() & 0xff) << 16)
				| ((input.readByte() & 0xff) << 24);
	}

	private int readWordValue(DataInputStream input) throws IOException {
		return (input.readByte() & 0xff) | ((input.readByte() & 0xff) << 8);
	}

	@Override
	public BufferedImage draw() {
		return _image;
	}

	@Override
	public int getHeight() {
		return this._biHeight;
	}

	@Override
	public int getWidth() {
		return this._biWidth;
	}

}
